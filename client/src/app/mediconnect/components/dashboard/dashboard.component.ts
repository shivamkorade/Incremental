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
  doctors: any[] = [];

  selectedClinicId: number | undefined;
  selectedClinicAppointments: any[] = [];

  constructor(private service: MediConnectService, private router: Router) {}

  ngOnInit(): void {
    this.role = localStorage.getItem('role') || '';
    this.userId = Number(localStorage.getItem('user_id'));

    console.log('Role from localStorage:', this.role);
    console.log('user_id from localStorage:', this.userId);

    if (this.role === 'DOCTOR') {
      this.doctorId = Number(localStorage.getItem('doctor_id'));
      console.log('Doctor ID:', this.doctorId);
      if (!this.doctorId) {
        console.error('Doctor ID missing from localStorage');
        return;
      }
      this.loadDoctorData();
    }

    if (this.role === 'PATIENT') {
      this.patientId = Number(localStorage.getItem('patient_id'));
      console.log('Patient ID:', this.patientId);
      if (!this.patientId) {
        console.error('Patient ID missing from localStorage');
        return;
      }
      this.loadPatientData();
    }
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/auth/login']);
  }

  // ================= DOCTOR =================
  loadDoctorData(): void {
    this.service.getDoctorById(this.doctorId).subscribe({
      next: (doctor) => {
        console.log('Doctor Details fetched:', doctor);
        this.doctorDetails = doctor;
      },
      error: (err) => {
        console.error('Failed to fetch doctor details:', err);
        this.doctorDetails = null;
      }
    });

    this.service.getClinicsByDoctorId(this.doctorId).subscribe({
      next: (clinics) => {
        console.log('Clinics fetched:', clinics);
        this.clinics = clinics;
      },
      error: (err) => {
        console.error('Failed to fetch clinics:', err);
        this.clinics = [];
      }
    });

    this.service.getAllPatients().subscribe({
      next: (patients) => {
        console.log('Patients fetched:', patients);
        this.doctors = patients; // reusing doctors array to display patients
      },
      error: () => {}
    });
  }

  loadAppointments(clinicId: number): void {
    this.service.getAppointmentsByClinic(clinicId).subscribe({
      next: (appts) => {
        this.selectedClinicAppointments = appts;
      },
      error: () => {
        this.selectedClinicAppointments = [];
      }
    });
  }

  onClinicSelect(clinic: any): void {
    this.selectedClinicId = clinic.clinicId;
    this.loadAppointments(clinic.clinicId);
  }

  // ✅ FIXED: wired to Edit button in HTML
  navigateToEditDoctor(): void {
    this.router.navigate(['/mediconnect/doctor/edit', this.doctorId]);
  }

  // ✅ NEW: navigate to clinic edit page
  navigateToEditClinic(clinicId: number): void {
    this.router.navigate(['/mediconnect/clinic/edit', clinicId]);
  }

  deleteDoctor(): void {
    if (confirm('Are you sure you want to delete your profile?')) {
      this.service.deleteDoctor(this.doctorId).subscribe({
        next: () => {
          localStorage.clear();
          this.router.navigate(['/auth/login']);
        },
        error: (err) => console.error('Delete doctor failed:', err)
      });
    }
  }

  deleteClinic(clinicId: number): void {
    if (confirm('Delete this clinic?')) {
      this.service.deleteClinic(clinicId).subscribe({
        next: () => {
          this.clinics = this.clinics.filter(c => c.clinicId !== clinicId);
          // Clear appointments if deleted clinic was selected
          if (this.selectedClinicId === clinicId) {
            this.selectedClinicId = undefined;
            this.selectedClinicAppointments = [];
          }
        },
        error: (err) => console.error('Delete clinic failed:', err)
      });
    }
  }

  cancelAppointment(appointment: any): void {
    if (confirm('Cancel this appointment?')) {
      appointment.status = 'Canceled';
      this.service.updateAppointment(appointment).subscribe({
        next: () => {
          console.log('Appointment cancelled');
          // Reload appointments for the selected clinic
          if (this.selectedClinicId) {
            this.loadAppointments(this.selectedClinicId);
          }
        },
        error: (err) => console.error('Cancel appointment failed:', err)
      });
    }
  }

  // ================= PATIENT =================
  loadPatientData(): void {
    this.service.getPatientById(this.patientId).subscribe({
      next: (patient) => {
        console.log('Patient Details fetched:', patient);
        this.patientDetails = patient;
      },
      error: (err) => {
        console.error('Failed to fetch patient details:', err);
        this.patientDetails = null;
      }
    });

    this.service.getAppointmentsByPatient(this.patientId).subscribe({
      next: (appointments) => {
        console.log('Patient appointments:', appointments);
        this.appointments = appointments;
      },
      error: () => { this.appointments = []; }
    });

    this.service.getAllClinics().subscribe({
      next: (clinics) => {
        console.log('All clinics:', clinics);
        this.clinics = clinics;
      },
      error: () => { this.clinics = []; }
    });

    this.service.getAllDoctors().subscribe({
      next: (doctors) => {
        console.log('All doctors:', doctors);
        this.doctors = doctors;
      },
      error: () => { this.doctors = []; }
    });
  }

  // ✅ FIXED: wired to Edit button in HTML
  navigateToEditPatient(): void {
    this.router.navigate(['/mediconnect/patient/edit', this.patientId]);
  }

  deletePatient(): void {
    if (confirm('Delete your profile?')) {
      this.service.deletePatient(this.patientId).subscribe({
        next: () => {
          localStorage.clear();
          this.router.navigate(['/auth/login']);
        },
        error: (err) => console.error('Delete patient failed:', err)
      });
    }
  }
}