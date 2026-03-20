import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
  selector: 'app-doctor-edit',
  templateUrl: './doctoredit.component.html',
  styleUrls: ['./doctoredit.component.scss']
})
export class DoctorEditComponent implements OnInit {
  doctorForm!: FormGroup;
  doctorId!: number;
  userId!: number;
  doctor: any;
  user: any;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private fb: FormBuilder, private service: MediConnectService) {}

  ngOnInit(): void {
    this.userId = Number(localStorage.getItem('user_id'));
    this.doctorId = this.userId; // mapping doctorId with userId for now

    this.doctorForm = this.fb.group({
      fullName: ['', Validators.required],
      username: ['', Validators.required],
      password: ['', Validators.required],
      specialty: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      //email: ['', [Validators.required, Validators.email]],
      email: ['default@example.com', [Validators.required, Validators.email]],      //add Default
      yearsOfExperience: ['', [Validators.required, Validators.min(1)]],
    });

    if (this.userId) {
      this.service.getDoctorById(this.userId).subscribe({
        next: (doctor) => {
          this.doctor = doctor;
          this.doctorForm.patchValue(doctor);
        },
        error: () => {
          this.doctor = undefined;
        }
      });

      this.service.getUserById(this.userId).subscribe({
        next: (user) => {
          this.user = user;
          this.doctorForm.patchValue(user);
        },
        error: () => {
          this.user = undefined;
        }
      });
    }
  }

  onSubmit(): void {
    if (this.doctorForm.valid) {
      this.service.updateDoctor(this.doctorId, this.doctorForm.value).subscribe({
        next: (res) => {
          this.successMessage = res.message || 'Doctor updated successfully!';
          this.errorMessage = null;
        },
        error: () => {
          this.errorMessage = 'Update failed. Please try again.';
          this.successMessage = null;
        }
      });
    }
  }
}