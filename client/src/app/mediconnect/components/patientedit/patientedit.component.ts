import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from 'src/app/mediconnect/services/mediconnect.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-patient-edit',
  templateUrl: './patientedit.component.html',
  styleUrls: ['./patientedit.component.scss']
})
export class PatientEditComponent implements OnInit {
  patientForm!: FormGroup;
  patientId!: number;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private service: MediConnectService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.patientId = Number(localStorage.getItem('patient_id'));
    this.initForm();

    if (this.patientId) {
      this.service.getPatientById(this.patientId).subscribe({
        next: (data) => {
          this.patientForm.patchValue(data);
        },
        error: () => {
          this.errorMessage = 'Failed to load patient details.';
        }
      });
    }
  }

  initForm(): void {
    this.patientForm = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      dateOfBirth: ['', Validators.required],
      address: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]]
    });
  }

  onSubmit(): void {
    if (this.patientForm.valid) {
      this.service.updatePatient(this.patientId, this.patientForm.value).subscribe({
        next: () => {
          this.successMessage = 'Profile updated successfully!';
          this.errorMessage = null;
          setTimeout(() => this.router.navigate(['/dashboard']), 1500);
        },
        error: () => {
          this.errorMessage = 'Update failed. Please try again.';
          this.successMessage = null;
        }
      });
    }
  }
}
