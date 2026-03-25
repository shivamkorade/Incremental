import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private service: MediConnectService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const paramId = this.route.snapshot.paramMap.get('id');
    this.doctorId = paramId
      ? Number(paramId)
      : Number(localStorage.getItem('doctor_id'));

    this.userId = Number(localStorage.getItem('user_id'));

    console.log('Editing doctor ID:', this.doctorId);
    console.log('User ID:', this.userId);

    // ✅ Form without username/password — they are handled separately
    this.doctorForm = this.fb.group({
      fullName:          ['', [Validators.required, Validators.minLength(2)]],
      specialty:         ['', Validators.required],
      contactNumber:     ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      email:             ['', [Validators.required, Validators.email]],
      yearsOfExperience: [null, [Validators.required, Validators.min(1)]],
    });

    // ✅ Load doctor details and patch form
    if (this.doctorId) {
      this.service.getDoctorById(this.doctorId).subscribe({
        next: (doctor) => {
          console.log('Doctor data loaded:', doctor);
          this.doctorForm.patchValue({
            fullName:          doctor.fullName,
            specialty:         doctor.specialty,
            contactNumber:     doctor.contactNumber,
            email:             doctor.email,
            yearsOfExperience: doctor.yearsOfExperience,
          });
        },
        error: (err) => {
          console.error('Failed to load doctor:', err);
          this.errorMessage = 'Failed to load doctor details.';
        }
      });
    }
  }

  // ✅ Getters for template validation
  get fullName()          { return this.doctorForm.get('fullName'); }
  get specialty()         { return this.doctorForm.get('specialty'); }
  get contactNumber()     { return this.doctorForm.get('contactNumber'); }
  get email()             { return this.doctorForm.get('email'); }
  get yearsOfExperience() { return this.doctorForm.get('yearsOfExperience'); }

  onSubmit(): void {
    if (this.doctorForm.invalid) {
      this.doctorForm.markAllAsTouched();
      this.errorMessage = 'Please fix the errors before submitting.';
      return;
    }

    // ✅ Get username and password from localStorage
    // so backend DoctorDTO gets all required fields
    const updatedDoctor = {
      doctorId:  this.doctorId,
      username:  localStorage.getItem('username') || '',
      password:  localStorage.getItem('password') || '',
      ...this.doctorForm.value
    };

    console.log('Submitting updated doctor:', updatedDoctor);

    this.service.updateDoctor(this.doctorId, updatedDoctor).subscribe({
      next: () => {
        this.successMessage = 'Doctor profile updated successfully!';
        this.errorMessage = null;
        setTimeout(() => this.router.navigate(['/mediconnect/dashboard']), 1500);
      },
      error: (err) => {
        console.error('Update failed:', err);
        this.errorMessage = err?.error?.message || 'Update failed. Please try again.';
        this.successMessage = null;
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/mediconnect/dashboard']);
  }
}