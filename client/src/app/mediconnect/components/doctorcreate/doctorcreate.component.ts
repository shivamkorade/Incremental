import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-doctor-create',
  templateUrl: './doctorcreate.component.html',
  styleUrls: ['./doctorcreate.component.scss']
})
export class DoctorCreateComponent implements OnInit {
  doctorForm!: FormGroup;
  successMessage = '';
  errorMessage = '';

  constructor(private formBuilder: FormBuilder) {}

  ngOnInit(): void {
    this.doctorForm = this.formBuilder.group({
      doctorId: ['', [Validators.required, Validators.min(1)]],
      fullName: ['', [Validators.required, Validators.minLength(2)]],
      specialty: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email: ['', [Validators.required, Validators.email]],
      yearsOfExperience: ['', [Validators.required, Validators.min(1)]],
    });
  }

  onSubmit(): void {
    if (this.doctorForm.valid) {
      this.successMessage = 'Doctor has been successfully created!';
      this.errorMessage = '';
      console.log('Doctor Created:', this.doctorForm.value);
    } else {
      this.errorMessage = 'Please fill out all required fields correctly.';
      this.successMessage = '';
    }
  }

  resetForm(): void {
    this.doctorForm.reset();
    this.successMessage = '';
    this.errorMessage = '';
  }
}

