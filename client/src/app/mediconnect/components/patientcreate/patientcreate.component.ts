import { Component, OnInit } from '@angular/core';

export interface Patient {
  patientId: number;   // NOTE: For tests, patientId should start as 0 (not null)
  fullName: string;
  dateOfBirth: string; // keep as string for date input binding
  contactNumber: string;
  email: string;
  address: string;
}

@Component({
  selector: 'app-patient-create',
  templateUrl: './patientcreate.component.html',
  styleUrls: ['./patientcreate.component.scss']
})
export class PatientCreateComponent implements OnInit {

  // Two-way bound patient model for template-driven forms
  patient: Patient = {
    patientId: 0,      // <-- Spec expects 0 as the initial value
    fullName: '',
    dateOfBirth: '',
    contactNumber: '',
    email: '',
    address: ''
  };

  successMessage = '';
  errorMessage = '';

  ngOnInit(): void {
    // No initialization required for template-driven form
  }

  // Handles form submission, validates input, and sets success/error messages.
  onSubmit(): void {
    if (this.isFormValid()) {
      this.successMessage = 'Patient has been successfully created!';
      this.errorMessage = '';
      // this.resetForm(); // clear after success
    } else {
      this.successMessage = '';
      this.errorMessage = 'Please fill out all required fields correctly.';
    }
  }

  // Validates patient details (ID, name, DOB, contact, email, address).
  isFormValid(): boolean {
    const idOk =
      typeof this.patient.patientId === 'number' &&
      this.patient.patientId > 0;

    const nameOk =
      typeof this.patient.fullName === 'string' &&
      this.patient.fullName.trim().length >= 3;

    const dobOk = !!this.patient.dateOfBirth;

    const contactPattern = /^\d{10}$/;
    const contactOk = contactPattern.test(this.patient.contactNumber || '');

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
    const emailOk = emailPattern.test(this.patient.email || '');

    const addressOk =
      typeof this.patient.address === 'string' &&
      this.patient.address.trim().length >= 5;

    return idOk && nameOk && dobOk && contactOk && emailOk && addressOk;
  }

  // Resets the patient model to default empty values.
  resetForm(): void {
    this.patient = {
      patientId: 0,  // <-- Keep 0 so the "initial empty object" test passes
      fullName: '',
      dateOfBirth: '',
      contactNumber: '',
      email: '',
      address: ''
    };
  }
}
