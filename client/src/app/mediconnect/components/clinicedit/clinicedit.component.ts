// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { ActivatedRoute, Router } from '@angular/router';
// import { MediConnectService } from '../../services/mediconnect.service';

// @Component({
//   selector: 'app-clinic-edit',
//   templateUrl: './clinicedit.component.html',
//   styleUrls: ['./clinicedit.component.scss']
// })
// export class ClinicEditComponent implements OnInit {
//   clinicForm!: FormGroup;
//   clinicId!: number;
//   successMessage: string = '';
//   errorMessage: string = '';

//   constructor(
//     private fb: FormBuilder,
//     private mediConnectService: MediConnectService,
//     private router: Router,
//     private route: ActivatedRoute
//   ) {}

//   ngOnInit(): void {
//     this.clinicId = Number(this.route.snapshot.paramMap.get('id'));

//     this.clinicForm = this.fb.group({
//       clinicName:      ['', [Validators.required, Validators.minLength(2)]],
//       location:        ['', Validators.required],
//       contactNumber:   ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
//       establishedYear: ['', [Validators.required, Validators.min(1900), Validators.max(2100)]],
//     });

//     this.mediConnectService.getAllClinics().subscribe({
//       next: (clinics: any[]) => {
//         const clinic = clinics.find(c => c.clinicId === this.clinicId);
//         if (clinic) {
//           this.clinicForm.patchValue({
//             clinicName:      clinic.clinicName,
//             location:        clinic.location,
//             contactNumber:   clinic.contactNumber,
//             establishedYear: clinic.establishedYear
//           });
//         }
//       },
//       error: () => { this.errorMessage = 'Failed to load clinic details.'; }
//     });
//   }

//   get clinicName()      { return this.clinicForm.get('clinicName'); }
//   get location()        { return this.clinicForm.get('location'); }
//   get contactNumber()   { return this.clinicForm.get('contactNumber'); }
//   get establishedYear() { return this.clinicForm.get('establishedYear'); }

//   onSubmit(): void {
//     if (this.clinicForm.invalid) {
//       this.clinicForm.markAllAsTouched();
//       this.errorMessage = 'Please fill all required fields correctly.';
//       return;
//     }

//     this.mediConnectService.updateClinic(this.clinicId, this.clinicForm.value).subscribe({
//       next: () => {
//         this.successMessage = 'Clinic updated successfully!';
//         this.errorMessage = '';
//         setTimeout(() => this.router.navigate(['/mediconnect/dashboard']), 1500);
//       },
//       error: (err) => {
//         this.errorMessage = err?.error?.message || 'Failed to update clinic.';
//         this.successMessage = '';
//       }
//     });
//   }

//   goBack(): void {
//     this.router.navigate(['/mediconnect/dashboard']);
//   }
// }

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
  selector: 'app-clinic-edit',
  templateUrl: './clinicedit.component.html',
  styleUrls: ['./clinicedit.component.scss']
})
export class ClinicEditComponent implements OnInit {
  clinicForm!: FormGroup;
  clinicId!: number;
  doctorId!: number; // ✅ IMPORTANT
  successMessage: string = '';
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private mediConnectService: MediConnectService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.clinicId = Number(this.route.snapshot.paramMap.get('id'));
    this.doctorId = Number(localStorage.getItem('doctor_id')); // ✅ IMPORTANT

    this.clinicForm = this.fb.group({
      clinicName:      ['', [Validators.required, Validators.minLength(2)]],
      location:        ['', Validators.required],
      contactNumber:   ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      establishedYear: ['', [Validators.required, Validators.min(1900), Validators.max(2100)]],
    });

    // ✅ Load clinic details
    this.mediConnectService.getAllClinics().subscribe({
      next: (clinics: any[]) => {
        const clinic = clinics.find(c => c.clinicId === this.clinicId);
        if (clinic) {
          this.clinicForm.patchValue({
            clinicName:      clinic.clinicName,
            location:        clinic.location,
            contactNumber:   clinic.contactNumber,
            establishedYear: clinic.establishedYear
          });
        }
      },
      error: () => {
        this.errorMessage = 'Failed to load clinic details.';
      }
    });
  }

  get clinicName()      { return this.clinicForm.get('clinicName'); }
  get location()        { return this.clinicForm.get('location'); }
  get contactNumber()   { return this.clinicForm.get('contactNumber'); }
  get establishedYear() { return this.clinicForm.get('establishedYear'); }

  // ✅ FIXED SUBMIT
  onSubmit(): void {
    if (this.clinicForm.invalid) {
      this.clinicForm.markAllAsTouched();
      this.errorMessage = 'Please fill all required fields correctly.';
      return;
    }

    // ✅ VERY IMPORTANT FIX
    const payload = {
      ...this.clinicForm.value,
      doctor: { doctorId: this.doctorId }   // ✅ keeps clinic linked to doctor
    };

    this.mediConnectService.updateClinic(this.clinicId, payload).subscribe({
      next: () => {
        this.successMessage = 'Clinic updated successfully!';
        this.errorMessage = '';
        setTimeout(() => this.router.navigate(['/mediconnect/dashboard']), 1500);
      },
      error: (err) => {
        this.errorMessage = err?.error?.message || 'Failed to update clinic.';
        this.successMessage = '';
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/mediconnect/dashboard']);
  }
}
