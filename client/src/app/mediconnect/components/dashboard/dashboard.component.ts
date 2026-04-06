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
  appointments: any[] = [];         // ✅ PATIENT appointments list
  doctors: any[] = [];              // DOCTOR view: derived patients OR patient view: all doctors

  selectedClinicId?: number;
  selectedClinicAppointments: any[] = [];  // ✅ DOCTOR clinic appointments list

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
    this.loadAllAppointments();

    // ✅ When you navigate back to dashboard, reload again (fixes stale view)
    this.navSub = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        this.initFromStorage();
        this.loadByRole();
      });

    // ✅ If we pass ?refresh=timestamp, reload (works even if component reused)
    this.qpSub = this.route.queryParams.subscribe(params => {
      if (params['refresh']) {
        this.initFromStorage();
        this.loadByRole();
      }
    });
  }

  ngOnDestroy(): void {
    this.navSub?.unsubscribe();
    this.qpSub?.unsubscribe();
  }

  loadAllAppointments() {
    console.log("appointment");
    const patient_id = Number(localStorage.getItem('patient_id'));
    console.log(patient_id);
    this.service.getAppointmentsByPatient(patient_id).subscribe(data => {
      console.log(data);
      this.appointments = data;
    })
  }

  private initFromStorage(): void {
    this.role = (localStorage.getItem('role') || '').trim().toUpperCase();
    this.userId = Number(localStorage.getItem('user_id'));

    this.doctorId = Number(localStorage.getItem('doctor_id'));
    this.patientId = Number(localStorage.getItem('patient_id'));

    // Helpful debug (optional)
    // console.log('ROLE:', this.role, 'doctorId:', this.doctorId, 'patientId:', this.patientId);
  }

  private loadByRole(): void {
    if (this.role === 'DOCTOR') {
      if (!this.doctorId) return;
      this.loadDoctorData();
    }

    if (this.role === 'PATIENT') {
      if (!this.patientId) return;
      this.loadPatientData();
    }
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/auth/login']);
  }

  // ===================== DOCTOR =====================

  loadDoctorData(): void {
    this.service.getDoctorById(this.doctorId).subscribe({
      next: doctor => this.doctorDetails = doctor,
      error: () => this.doctorDetails = null
    });

    this.service.getClinicsByDoctorId(this.doctorId).subscribe({
      next: clinics => {
        this.clinics = clinics || [];

        // Optional: auto-select first clinic to show appointments immediately
        if (this.clinics.length > 0) {
          this.onClinicSelect(this.clinics[0]);
        }
      },
      error: () => this.clinics = []
    });

    this.doctors = [];
  }

  onClinicSelect(clinic: any): void {
    this.selectedClinicId = clinic?.clinicId;
    if (this.selectedClinicId) {
      this.loadAppointments(this.selectedClinicId);
    }
  }

  loadAppointments(clinicId: number): void {
    this.service.getAppointmentsByClinic(clinicId).subscribe({
      next: appts => {
        this.selectedClinicAppointments = appts || [];

        // derive unique patients from appointments (doctor view)
        const patientMap = new Map<number, any>();
        (appts || []).forEach(a => {
          const p = a?.patient || a?.patientDetails || null;
          const pid = p?.patientId || a?.patientId || null;
          if (pid) patientMap.set(pid, p || { patientId: pid });
        });

        this.doctors = Array.from(patientMap.values());
      },
      error: () => {
        this.selectedClinicAppointments = [];
        this.doctors = [];
      }
    });
  }

  navigateToEditDoctor(): void {
    this.router.navigate(['/mediconnect/doctor/edit', this.doctorId]);
  }

  deleteDoctor(): void {
    if (confirm('Are you sure you want to delete your profile?')) {
      this.service.deleteDoctor(this.doctorId).subscribe(() => {
        localStorage.clear();
        this.router.navigate(['/auth/login']);
      });
    }
  }

  cancelAppointment(appointment: any): void {
    if (confirm('Cancel this appointment?')) {
      appointment.status = 'Canceled';
      this.service.updateAppointment(appointment).subscribe(() => {
        if (this.selectedClinicId) {
          this.loadAppointments(this.selectedClinicId);
        }
      });
    }
  }

  // ===================== PATIENT =====================

  loadPatientData(): void {
    this.service.getPatientById(this.patientId).subscribe({
      next: patient => this.patientDetails = patient,
      error: () => this.patientDetails = null
    });

    // this.service.getAppointmentsByPatient(this.patientId).subscribe({
    //   next: appts => {
    //     // console.log('Patient appointments:', appts);
    //     this.appointments = appts || [];
    //   },
    //   error: () => {
    //     this.appointments = [];
    //   }
    // });

    this.service.getAllClinics().subscribe({
      next: clinics => this.clinics = clinics || [],
      error: () => this.clinics = []
    });

    this.service.getAllDoctors().subscribe({
      next: doctors => this.doctors = doctors || [],
      error: () => this.doctors = []
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
}