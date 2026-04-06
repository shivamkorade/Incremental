import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
  doctorId!: number;

  constructor(
    private fb: FormBuilder,
    private mediConnectService: MediConnectService,
    private router: Router
  ) {}

  ngOnInit(): void {
    //  Get doctorId from localStorage
    this.doctorId = Number(localStorage.getItem('doctor_id'));

    this.clinicForm = this.fb.group({
      clinicName:      ['', [Validators.required, Validators.minLength(2)]],
      location:        ['', Validators.required],
      contactNumber:   ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      establishedYear: ['', [Validators.required, Validators.min(1900), Validators.max(2100)]],
    });

    // ✅ Load doctor details to display name
    if (this.doctorId) {
      this.mediConnectService.getDoctorById(this.doctorId).subscribe({
        next: (doctor) => {
          this.doctor = doctor;
        },
        error: (err) => {
          console.error('Failed to load doctor:', err);
        }
      });
    }
  }

  get clinicName()      { return this.clinicForm.get('clinicName'); }
  get location()        { return this.clinicForm.get('location'); }
  get contactNumber()   { return this.clinicForm.get('contactNumber'); }
  get establishedYear() { return this.clinicForm.get('establishedYear'); }

  onSubmit(): void {
    if (this.clinicForm.invalid) {
      this.clinicForm.markAllAsTouched();
      this.errorMessage = 'Please fill all required fields correctly.';
      return;
    }

    //  FIXED: send doctorId as number, not whole doctor object
    const clinicPayload = {
      ...this.clinicForm.value,
      doctorId: this.doctorId
    };

    console.log('Creating clinic:', clinicPayload);

    this.mediConnectService.addClinic(clinicPayload).subscribe({
      next: () => {
        this.successMessage = 'Clinic created successfully!';
        this.errorMessage = '';
        setTimeout(() => this.router.navigate(['/mediconnect/dashboard']), 1500);
      },
      error: (err) => {
        console.error('Create clinic failed:', err);
        if (err.status === 400) {
          this.errorMessage = 'Bad request. Please check your input.';
        } else if (err.status === 401) {
          this.errorMessage = 'Unauthorized. Please login again.';
        } else {
          this.errorMessage = err?.error?.message || 'An unexpected error occurred.';
        }
        this.successMessage = '';
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/mediconnect/dashboard']);
  }
}