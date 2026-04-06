import { Component, OnDestroy, OnInit } from '@angular/core';
import { MediConnectService } from '../../services/mediconnect.service';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, Subscription } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, OnDestroy {

  role: string = '';
  doctorId!: number;
  patientId!: number;
  userId!: number;

  doctorDetails: any = null;
  patientDetails: any = null;

  clinics: any[] = [];
  appointments: any[] = [];
  doctors: any[] = [];

  selectedClinicId?: number;
  selectedClinicAppointments: any[] = [];

  // Bill cache keyed by appointmentId
  billCache: { [apptId: number]: any } = {};

  // Billing modal
  showBillingModal = false;
  selectedBill: any = null;
  selectedApptForBill: any = null;
  paymentSuccess = false;
  selectedPaymentMethod = 'upi';

  // Doctor notes modal
  showNotesModal = false;
  selectedApptForNotes: any = null;
  notesInput = '';
  notesSaved = false;

  private navSub?: Subscription;
  private qpSub?: Subscription;

  constructor(
    private service: MediConnectService,
    private router: Router,
    private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.initFromStorage();
    this.loadByRole();
    if (this.role === 'PATIENT') this.loadAllAppointments();

    this.navSub = this.router.events
      .pipe(filter(e => e instanceof NavigationEnd))
      .subscribe(() => { this.initFromStorage(); this.loadByRole(); });

    this.qpSub = this.route.queryParams.subscribe(params => {
      if (params['refresh']) {
        this.initFromStorage();
        this.loadByRole();
        if (this.role === 'PATIENT') this.loadAllAppointments();
      }
    });
  }

  ngOnDestroy(): void {
    this.navSub?.unsubscribe();
    this.qpSub?.unsubscribe();
  }

  private initFromStorage(): void {
    this.role = (localStorage.getItem('role') || '').trim().toUpperCase();
    this.userId = Number(localStorage.getItem('user_id'));
    this.doctorId = Number(localStorage.getItem('doctor_id'));
    this.patientId = Number(localStorage.getItem('patient_id'));
  }

  private loadByRole(): void {
    if (this.role === 'DOCTOR' && this.doctorId) this.loadDoctorData();
    if (this.role === 'PATIENT' && this.patientId) this.loadPatientData();
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/auth/login']);
  }

  // ===================== PATIENT =====================

  loadAllAppointments(): void {
    if (!this.patientId) return;
    this.service.getAppointmentsByPatient(this.patientId).subscribe(data => {
      this.appointments = data || [];
      // Pre-fetch bills for approved appointments
      this.appointments
        .filter(a => a.status === 'Approved')
        .forEach(a => this.prefetchBill(a.appointmentId));
    });
  }

  prefetchBill(appointmentId: number): void {
    if (this.billCache[appointmentId]) return;
    this.service.getBillByAppointment(appointmentId).subscribe({
      next: bill => { this.billCache[appointmentId] = bill; },
      error: () => { this.billCache[appointmentId] = null; }
    });
  }

  loadPatientData(): void {
    this.service.getPatientById(this.patientId).subscribe({
      next: p => this.patientDetails = p,
      error: () => this.patientDetails = null
    });
    this.service.getAllClinics().subscribe({ next: c => this.clinics = c || [], error: () => this.clinics = [] });
    this.service.getAllDoctors().subscribe({ next: d => this.doctors = d || [], error: () => this.doctors = [] });
  }

  getBillForAppt(apptId: number): any { return this.billCache[apptId]; }

  openBillingModal(appt: any): void {
    this.paymentSuccess = false;
    this.selectedPaymentMethod = 'upi';
    this.selectedApptForBill = appt;
    const cached = this.billCache[appt.appointmentId];
    if (cached !== undefined) {
      this.selectedBill = cached ?? { _notFound: true };
      this.showBillingModal = true;
    } else {
      this.service.getBillByAppointment(appt.appointmentId).subscribe({
        next: bill => {
          this.billCache[appt.appointmentId] = bill;
          this.selectedBill = bill;
          this.showBillingModal = true;
        },
        error: () => {
          this.selectedBill = { _notFound: true };
          this.showBillingModal = true;
        }
      });
    }
  }

  closeBillingModal(): void {
    this.showBillingModal = false;
    this.selectedBill = null;
    this.selectedApptForBill = null;
    this.paymentSuccess = false;
  }

  payBill(): void {
    if (!this.selectedBill?.billingId) return;
    const updated = { ...this.selectedBill, status: 'Paid', paymentMethod: this.selectedPaymentMethod };
    this.service.payBill(this.selectedBill.billingId, updated).subscribe({
      next: (res) => {
        this.selectedBill = { ...this.selectedBill, status: 'Paid', paymentMethod: this.selectedPaymentMethod };
        if (this.selectedApptForBill) {
          this.billCache[this.selectedApptForBill.appointmentId] = this.selectedBill;
        }
        this.paymentSuccess = true;
        this.loadAllAppointments();
      },
      error: () => {
        // Still mark locally for demo
        this.selectedBill = { ...this.selectedBill, status: 'Paid', paymentMethod: this.selectedPaymentMethod };
        if (this.selectedApptForBill) {
          this.billCache[this.selectedApptForBill.appointmentId] = this.selectedBill;
        }
        this.paymentSuccess = true;
        this.loadAllAppointments();
      }
    });
  }

  navigateToEditPatient(): void {
    this.router.navigate(['/mediconnect/patient/edit', this.patientId]);
  }

  deletePatient(): void {
    if (confirm('Delete your profile?')) {
      this.service.deletePatient(this.patientId).subscribe(() => {
        localStorage.clear();
        this.router.navigate(['/auth/login']);
      });
    }
  }

  // ===================== DOCTOR =====================

  loadDoctorData(): void {
    this.service.getDoctorById(this.doctorId).subscribe({
      next: d => this.doctorDetails = d,
      error: () => this.doctorDetails = null
    });
    this.service.getClinicsByDoctorId(this.doctorId).subscribe({
      next: clinics => {
        this.clinics = clinics || [];
        if (this.clinics.length > 0) this.onClinicSelect(this.clinics[0]);
      },
      error: () => this.clinics = []
    });
    this.doctors = [];
  }

  onClinicSelect(clinic: any): void {
    this.selectedClinicId = clinic?.clinicId;
    if (this.selectedClinicId) this.loadAppointments(this.selectedClinicId);
  }

  loadAppointments(clinicId: number): void {
    this.service.getAppointmentsByClinic(clinicId).subscribe({
      next: appts => {
        this.selectedClinicAppointments = appts || [];
        // Fetch billing info for each appointment so doctor sees payment method
        this.selectedClinicAppointments.forEach(a => {
          if (!this.billCache[a.appointmentId]) {
            this.service.getBillByAppointment(a.appointmentId).subscribe({
              next: bill => { this.billCache[a.appointmentId] = bill; },
              error: () => { this.billCache[a.appointmentId] = null; }
            });
          }
        });
        const patientMap = new Map<number, any>();
        (appts || []).forEach(a => {
          const p = a?.patient;
          const pid = p?.patientId;
          if (pid) patientMap.set(pid, p);
        });
        this.doctors = Array.from(patientMap.values());
      },
      error: () => { this.selectedClinicAppointments = []; this.doctors = []; }
    });
  }

  approveAppointment(appt: any): void {
    this.service.approveAppointment(appt.appointmentId).subscribe({
      next: () => {
        appt.status = 'Approved';
        const bill = {
          patient: { patientId: appt.patient?.patientId || appt.patientId },
          appointmentId: appt.appointmentId,
          amount: 500,
          dateOfIssue: new Date(),
          dueDate: new Date(Date.now() + 7 * 24 * 60 * 60 * 1000),
          status: 'Pending',
          paymentMethod: null
        };
        this.service.createBill(bill).subscribe({
          next: () => { if (this.selectedClinicId) this.loadAppointments(this.selectedClinicId); }
        });
      }
    });
  }

  rejectAppointment(appt: any): void {
    if (confirm('Reject this appointment?')) {
      this.service.rejectAppointment(appt.appointmentId).subscribe({
        next: () => {
          appt.status = 'Rejected';
          if (this.selectedClinicId) this.loadAppointments(this.selectedClinicId);
        }
      });
    }
  }

  cancelAppointment(appt: any): void {
    if (confirm('Cancel this appointment?')) {
      appt.status = 'Canceled';
      this.service.updateAppointment(appt).subscribe(() => {
        if (this.selectedClinicId) this.loadAppointments(this.selectedClinicId);
      });
    }
  }

  // Doctor Notes
  openNotesModal(appt: any): void {
    this.selectedApptForNotes = appt;
    this.notesInput = appt.doctorNotes || '';
    this.notesSaved = false;
    this.showNotesModal = true;
  }

  closeNotesModal(): void {
    this.showNotesModal = false;
    this.selectedApptForNotes = null;
    this.notesInput = '';
    this.notesSaved = false;
  }

  saveDoctorNotes(): void {
    if (!this.selectedApptForNotes) return;
    this.service.addDoctorNotes(this.selectedApptForNotes.appointmentId, this.notesInput).subscribe({
      next: () => {
        this.selectedApptForNotes.doctorNotes = this.notesInput;
        this.notesSaved = true;
        if (this.selectedClinicId) this.loadAppointments(this.selectedClinicId);
      },
      error: () => {
        // Save locally for demo
        this.selectedApptForNotes.doctorNotes = this.notesInput;
        this.notesSaved = true;
      }
    });
  }

  navigateToEditDoctor(): void {
    this.router.navigate(['/mediconnect/doctor/edit', this.doctorId]);
  }

  deleteDoctor(): void {
    if (confirm('Delete your doctor profile?')) {
      this.service.deleteDoctor(this.doctorId).subscribe(() => {
        localStorage.clear();
        this.router.navigate(['/auth/login']);
      });
    }
  }
}
