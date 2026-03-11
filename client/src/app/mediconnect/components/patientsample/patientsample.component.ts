

import { Component } from '@angular/core';

@Component({
  selector: 'app-patient-sample',
  standalone: true,
  templateUrl: './patientsample.component.html',
  styleUrls: ['./patientsample.component.scss']
})
export class PatientSampleComponent {
  patient = {
    patientId: 1,
    fullName: 'John Doe',
    dateOfBirth: '1990-01-01', // plain string (no Date object)
    contactNumber: '1234567890',
    email: 'john@example.com',
    address: '123 Main Street, Cityville'
  };

  logPatientAttributes(): void {
    console.log(this.patient);
  }
}
