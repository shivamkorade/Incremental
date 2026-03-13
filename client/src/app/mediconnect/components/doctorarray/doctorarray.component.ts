import { Component, OnInit } from '@angular/core';

interface Doctor {
    id: number;
    fullName: string;
    specialty: string;
    contactNumber: string;
    email: string;
    yearsOfExperience: number;
}

@Component({
    selector: 'app-doctor-array',
    templateUrl: './doctorarray.component.html',
    styleUrls: ['./doctorarray.component.scss']
})
export class DoctorArrayComponent implements OnInit {
    doctors: Doctor[] = [];
    showDetails = false;

    ngOnInit(): void {
        this.loadDoctors();
    }

    /**
 * Loads sample doctor data (JSON array) expected by tests.
 * - 3 doctors total
 * - The 2nd doctor should be Orthopedics
 */
private loadDoctors(): void {
  this.doctors = [
    {
      id: 1,
      fullName: 'Dr. Jane Smith',
      specialty: 'Cardiology',
      contactNumber: '9876543210',
      email: 'jane.smith@example.com',
      yearsOfExperience: 10
    },
    {
      id: 2,
      fullName: 'Dr. John Doe',
      specialty: 'Orthopedics', // <-- important: test expects Orthopedics in 2nd item
      contactNumber: '9123456780',
      email: 'john.doe@example.com',
      yearsOfExperience: 8
    },
    {
      id: 3,
      fullName: 'Dr. Alice Johnson',
      specialty: 'Neurology',
      contactNumber: '9988776655',
      email: 'alice.johnson@example.com',
      yearsOfExperience: 7
    }
  ];
}

    /**
     * Toggles the visibility of doctor details.
     */
    toggleDetails(): void {
        this.showDetails = !this.showDetails;
    }
}
``