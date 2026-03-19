import { Component, OnInit } from '@angular/core';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  role: string = '';

  doctorId: number = 1;
  patientId: number = 1;

  doctorDetails: any;
  patientDetails: any;
patient:any;
  clinics: any[] = [];

  // Day 23 requirement
  selectClinicAppointments: any[] = [];

  // Day 24 requirement
  appointments: any[] = [];

  constructor(private service: MediConnectService) {}

  ngOnInit(): void {
    this.role = localStorage.getItem('role') || '';

    if (this.role === 'DOCTOR') {
      this.doctorId = Number(localStorage.getItem('user_id')) || 1;
      this.loadDoctorDetails();
    }

    if (this.role === 'PATIENT') {
      this.patientId = Number(localStorage.getItem('user_id')) || 1;
      this.loadPatientData();
    }
  }

  /* ---------------------- DOCTOR MODE (DAY 23) ---------------------- */

  loadDoctorDetails(): void {
    this.loadDoctorData();
  }

  loadDoctorData(): void {
    if (!this.doctorId) this.doctorId = 1;

    // Test expects clinics FIRST
    this.service.getClinicsByDoctorId(this.doctorId).subscribe({
      next: (clinics) => {
        this.clinics = clinics;

        if (clinics && clinics.length > 0) {
          this.loadAppointments(clinics[0].clinicId);
        }
      }
    });

    // Then doctor details
    this.service.getDoctorById(this.doctorId).subscribe({
      next: (doctor) => {
        this.doctorDetails = doctor;
      },
      error: () => {
        this.doctorDetails = undefined;
      }
    });
  }

loadPatientData(): void {
  if (!this.patientId) this.patientId = 1;

  // 1. Fetch appointments FIRST (test spies this first)
  this.service.getAppointmentsByPatient(this.patientId).subscribe(appt => {
    this.appointments = appt;
  });

  // 2. Fetch clinics SECOND
  this.service.getAllClinics().subscribe(clinics => {
    this.clinics = clinics;
  });

  // 3. Fetch patient details THIRD
  this.service.getPatientById(this.patientId).subscribe({
    next: (patient) => {
      this.patientDetails = patient;
      this.patient = patient;  
    },
    error: () => {
      this.patientDetails = undefined;
      this.patient = undefined;
    }
  });
}

  deletePatient(): void {
    this.service.deletePatient(this.patientId).subscribe({
      next: () => {
        this.patientDetails = undefined;
      }
    });
  }

  /* ---------------------- SHARED ---------------------- */

  loadAppointments(clinicId: number): void {
    this.service.getAppointmentsByClinic(clinicId).subscribe({
      next: (appointments) => {
        this.selectClinicAppointments = appointments;
      }
    });
  }
}