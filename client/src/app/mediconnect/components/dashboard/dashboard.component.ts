import { Component, OnInit } from '@angular/core';
import { MediConnectService } from '../../services/mediconnect.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  role: string = '';
  doctorId!: number;
  patientId!: number;
  userId!: number;

  doctorDetails: any = null;
  patientDetails: any = null;

  clinics: any[] = [];
  appointments: any[] = [];
  doctors: any[] = []; // ✅ REGISTERED PATIENTS (derived from appointments)

  selectedClinicId?: number;
  selectedClinicAppointments: any[] = [];

  showBookingModal: boolean = false;

  constructor(private service: MediConnectService, private router: Router) {}

  ngOnInit(): void {
    this.role = (localStorage.getItem('role') || '').trim().toUpperCase();
    this.userId = Number(localStorage.getItem('user_id'));

    if (this.role === 'DOCTOR') {
      this.doctorId = Number(localStorage.getItem('doctor_id'));
      if (!this.doctorId) return;
      this.loadDoctorData();
    }

    if (this.role === 'PATIENT') {
      this.patientId = Number(localStorage.getItem('patient_id'));
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
      next: clinics => this.clinics = clinics,
      error: () => this.clinics = []
    });

    // ✅ clear patients until clinic is selected
    this.doctors = [];
  }

  onClinicSelect(clinic: any): void {
    this.selectedClinicId = clinic.clinicId;
    this.loadAppointments(clinic.clinicId);
  }

  // ✅ OPTION 1 CORE LOGIC
  loadAppointments(clinicId: number): void {
    this.service.getAppointmentsByClinic(clinicId).subscribe({
      next: appts => {
        this.selectedClinicAppointments = appts;

        // ✅ derive unique patients from appointments
        const patientMap = new Map<number, any>();

        appts.forEach(a => {
          if (a.patient) {
            patientMap.set(a.patient.patientId, a.patient);
          }
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

  navigateToEditClinic(clinicId: number): void {
    this.router.navigate(['/mediconnect/clinic/edit', clinicId]);
  }

  deleteDoctor(): void {
    if (confirm('Are you sure you want to delete your profile?')) {
      this.service.deleteDoctor(this.doctorId).subscribe(() => {
        localStorage.clear();
        this.router.navigate(['/auth/login']);
      });
    }
  }

  deleteClinic(clinicId: number): void {
    if (confirm('Delete this clinic?')) {
      this.service.deleteClinic(clinicId).subscribe(() => {
        this.clinics = this.clinics.filter(c => c.clinicId !== clinicId);
        if (this.selectedClinicId === clinicId) {
          this.selectedClinicId = undefined;
          this.selectedClinicAppointments = [];
          this.doctors = [];
        }
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

    this.service.getAppointmentsByPatient(this.patientId).subscribe({
      next: appointments => this.appointments = appointments,
      error: () => this.appointments = []
    });

    this.service.getAllClinics().subscribe({
      next: clinics => this.clinics = clinics,
      error: () => this.clinics = []
    });

    this.service.getAllDoctors().subscribe({
      next: doctors => this.doctors = doctors,
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