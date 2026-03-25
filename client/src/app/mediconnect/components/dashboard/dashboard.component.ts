import { Component, OnInit } from '@angular/core';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  role: string = '';

  doctorId!: number;
  patientId!: number;

  doctorDetails: any;
  patientDetails: any;

  clinics: any[] = [];
  appointments: any[] = [];

  constructor(private service: MediConnectService) {}

  ngOnInit(): void {
    this.role = localStorage.getItem('role') || '';

    // ✅ DOCTOR FLOW
    if (this.role === 'DOCTOR') {
      this.doctorId = Number(localStorage.getItem('userId'));

      if (!this.doctorId) {
        console.error('Doctor ID missing');
        return;
      }

      this.loadDoctorData();
    }

    // ✅ PATIENT FLOW
    if (this.role === 'PATIENT') {
      this.patientId = Number(localStorage.getItem('userId'));

      if (!this.patientId) {
        console.error('Patient ID missing');
        return;
      }

      this.loadPatientData();
    }
  }

  // ================= DOCTOR =================

  loadDoctorData(): void {
    this.service.getDoctorById(this.doctorId).subscribe({
    next: (doctor) => {
    console.log("API doctor response:", doctor);
    this.doctorDetails = doctor;
    }
    });
    // Doctor details
    this.service.getDoctorById(this.doctorId).subscribe({
      next: (doctor) => this.doctorDetails = doctor,
      error: () => this.doctorDetails = undefined
    });

    // Clinics
    this.service.getClinicsByDoctorId(this.doctorId).subscribe({
      next: (clinics) => this.clinics = clinics
    });

    // ✅ Appointments (FIXED)
    this.service.getAppointmentsByDoctorId(this.doctorId).subscribe({
      next: (appointments) => this.appointments = appointments
    });
  }

  deleteDoctor(): void {
    if (confirm('Are you sure you want to delete your profile?')) {
      this.service.deleteDoctor(this.doctorId).subscribe(() => {
        this.doctorDetails = null;
      });
    }
  }

  deleteClinic(clinicId: number): void {
    if (confirm('Delete this clinic?')) {
      this.service.deleteClinic(clinicId).subscribe(() => {
        this.clinics = this.clinics.filter(c => c.clinicId !== clinicId);
      });
    }
  }

  cancelAppointment(appointment: any): void {
    if (confirm('Cancel this appointment?')) {
      appointment.status = 'Cancelled';
      this.service.updateAppointment(appointment).subscribe();
    }
  }

  // ================= PATIENT =================

  loadPatientData(): void {

    // Patient details
    this.service.getPatientById(this.patientId).subscribe({
      next: (patient) => this.patientDetails = patient,
      error: () => this.patientDetails = undefined
    });

    // Appointments
    this.service.getAppointmentsByPatient(this.patientId).subscribe({
      next: (appointments) => this.appointments = appointments
    });

    // Clinics
    this.service.getAllClinics().subscribe({
      next: (clinics) => this.clinics = clinics
    });
  }

  deletePatient(): void {
    if (confirm('Delete your profile?')) {
      this.service.deletePatient(this.patientId).subscribe(() => {
        this.patientDetails = null;
      });
    }
  }
}