

import { Component } from '@angular/core';

@Component({
  selector: 'app-doctor-sample',
  standalone: true,
  templateUrl: './doctorsample.component.html',
  styleUrls: ['./doctorsample.component.scss']
})
export class DoctorSampleComponent {
  doctor = {
    doctorId: 1,
    fullName: 'Dr. Jane Smith',
    specialty: 'Cardiology',
    contactNumber: '9876543210',
    email: 'jane@example.com',
    yearsOfExperience: 15
  };

  logDoctorAttributes(): void {
    console.log(this.doctor);
  }
}

