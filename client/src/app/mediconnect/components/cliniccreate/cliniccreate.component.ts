

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
  selector: 'app-cliniccreate',
  templateUrl: './cliniccreate.component.html',
  styleUrls: ['./cliniccreate.component.scss']
})
export class ClinicCreateComponent implements OnInit {
  clinicForm!: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';
  doctor: any;

  constructor(
    private fb: FormBuilder,
    private mediConnectService: MediConnectService
  ) {}

  ngOnInit(): void {
    this.clinicForm = this.fb.group({
      doctor: [''],
      clinicId: [''],
      clinicName: ['', Validators.required],
      location: ['', Validators.required],
      contactNumber: ['', Validators.required],
      establishedYear: ['', Validators.required],
    });

    const doctorId = Number(localStorage.getItem('doctor_id'));
    this.mediConnectService.getDoctorById(doctorId).subscribe((doctor) => {
      this.doctor = doctor;
      this.clinicForm.patchValue({ doctor: doctor });
    });
  }

  onSubmit(): void {
    if (this.clinicForm.valid) {
      this.mediConnectService.addClinic(this.clinicForm.value).subscribe({
        next: () => {
          this.successMessage = 'Clinic created successfully!';
          this.errorMessage = '';
        },
        error: (err) => {
          if (err.status === 400) {
            this.errorMessage = 'Bad request. Please check your input.';
          } else {
            this.errorMessage = 'An unexpected error occurred.';
          }
        }
      });
    }
  }
}
