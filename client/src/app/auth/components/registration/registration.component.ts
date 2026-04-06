// import { Component, OnInit } from '@angular/core';
// import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
// import { AuthService } from '../../services/auth.service';

// type Role = 'DOCTOR' | 'PATIENT';

// @Component({
//   selector: 'app-registration',
//   templateUrl: './registration.component.html',
//   styleUrls: ['./registration.component.scss']
// })
// export class RegistrationComponent implements OnInit {
//   registrationForm!: FormGroup;
//   successMessage: string | null = null;
//   errorMessage: string | null = null;
//   selectedRole: Role | null = null;

//   constructor(
//     private formBuilder: FormBuilder,
//     private authService: AuthService
//   ) {}

//   ngOnInit(): void {
//     this.registrationForm = this.formBuilder.group({
//       username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
//       password: [
//         '',
//         [
//           Validators.required,
//           Validators.minLength(8),
//           Validators.pattern(/^(?=.*[A-Z])(?=.*\d).+$/)
//         ]
//       ],
//       role: ['', Validators.required],
//       fullName: ['', Validators.required],
//       contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
//       email: ['', [Validators.required, Validators.email]],

//       // optional
//       specialty: [''],
//       yearsOfExperience: [''],
//       dateOfBirth: ['', [Validators.required, this.noFutureDateValidator]],
//       address: ['']
//     });

//     this.registrationForm.get('role')?.valueChanges.subscribe((role: Role) => {
//       this.onRoleChangeInternal(role);
//     });
//   }

//   noFutureDateValidator(control: AbstractControl) {
//   const selected = new Date(control.value);
//   const today = new Date();
//   if (selected > today) {
//     return { futureDate: true };
//   }
//   return null;
// }

//   private setRequired(ctrl: AbstractControl | null, required: boolean): void {
//     if (!ctrl) return;
//     ctrl.setValidators(required ? [Validators.required] : []);
//     ctrl.updateValueAndValidity({ emitEvent: false });
//   }

//   private onRoleChangeInternal(role: Role): void {
//     this.selectedRole = role;

//     const specialty = this.registrationForm.get('specialty');
//     const yoe = this.registrationForm.get('yearsOfExperience');
//     const dob = this.registrationForm.get('dateOfBirth');
//     const address = this.registrationForm.get('address');

//     specialty?.reset();
//     yoe?.reset();
//     dob?.reset();
//     address?.reset();

//     if (role === 'DOCTOR') {
//       this.setRequired(specialty, true);
//       this.setRequired(yoe, true);
//       this.setRequired(dob, false);
//       this.setRequired(address, false);
//     } else if (role === 'PATIENT') {
//       this.setRequired(specialty, false);
//       this.setRequired(yoe, false);
//       this.setRequired(dob, true);
//       this.setRequired(address, true);
//     }
//   }

//   onRoleChange(event: Event): void {
//     const role = (event.target as HTMLSelectElement).value as Role;
//     this.onRoleChangeInternal(role);
//   }

//   onSubmit(): void {
//   this.successMessage = null;
//   this.errorMessage = null;

//   if (this.registrationForm.invalid) {
//     this.registrationForm.markAllAsTouched();
//     this.errorMessage = 'Please fill all fields correctly';
//     return;
//   }

//   const formValue = this.registrationForm.value;

//   let payload: any = {
//     username: formValue.username,
//     password: formValue.password,
//     role: formValue.role,
//     fullName: formValue.fullName,
//     contactNumber: formValue.contactNumber,
//     email: formValue.email
//   };

//   // ✅ ADD ROLE-BASED FIELDS (CRITICAL FIX)
//   if (formValue.role === 'DOCTOR') {
//     payload.specialty = formValue.specialty || '';
//     payload.yearsOfExperience = formValue.yearsOfExperience
//       ? Number(formValue.yearsOfExperience)
//       : 0;   // 🔥 prevents null crash
//   }

//   if (formValue.role === 'PATIENT') {
//     payload.dateOfBirth = formValue.dateOfBirth || null;
//     payload.address = formValue.address || '';
//   }

//   console.log('Sending payload:', payload); // ✅ DEBUG

//   this.authService.createUser(payload).subscribe({
//     next: (res) => {
//       console.log('User created:', res);
//       this.successMessage = 'Registration successful!';
//       this.resetForm();
//     },
//     error: (err) => {
//   console.error('Registration error:', err);

//   // Extract backend message safely
//   const backendMsg =
//     err?.error?.message ||    // Spring Boot @ExceptionHandler returns here
//     err?.error?.error ||      // Some APIs return { error: "message" }
//     err?.error?.details ||    // Validation frameworks use this
//     err?.message;             // Fallback

//   this.errorMessage = backendMsg || 'Registration failed. Please try again.';

//   // OPTIONAL: Mark email field as duplicate
//   if (backendMsg && backendMsg.toLowerCase().includes('email')) {
//     this.email?.setErrors({ duplicate: true });
//   }
// }

//   });
// }

//   resetForm(): void {
//     this.registrationForm.reset();
//     this.selectedRole = null;
//   }

//   // ✅ ALL REQUIRED GETTERS (fixes your error)
//   get username() { return this.registrationForm.get('username'); }
//   get password() { return this.registrationForm.get('password'); }
//   get role() { return this.registrationForm.get('role'); }
//   get fullName() { return this.registrationForm.get('fullName'); }
//   get contactNumber() { return this.registrationForm.get('contactNumber'); }
//   get email() { return this.registrationForm.get('email'); }

//   get specialty() { return this.registrationForm.get('specialty'); }
//   get yearsOfExperience() { return this.registrationForm.get('yearsOfExperience'); }

//   get dateOfBirth() { return this.registrationForm.get('dateOfBirth'); }
//   get address() { return this.registrationForm.get('address'); }
// }

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidatorFn } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

type Role = 'DOCTOR' | 'PATIENT';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  registrationForm!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;
  selectedRole: Role | null = null;

  // ✅ Keep validator sets so role switching doesn't wipe rules
  private readonly doctorSpecialtyValidators: ValidatorFn[] = [Validators.required];
  private readonly doctorYoeValidators: ValidatorFn[] = [Validators.required, Validators.min(0)];
  private readonly patientDobValidators: ValidatorFn[] = [Validators.required, this.noFutureDateValidator];
  private readonly patientAddressValidators: ValidatorFn[] = [Validators.required];

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router // ✅ ADD: needed for redirect to login
  ) {}

  ngOnInit(): void {
    this.registrationForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.pattern(/^(?=.*[A-Z])(?=.*\d).+$/)
        ]
      ],
      role: ['', Validators.required],
      fullName: ['', Validators.required],
      contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      email: ['', [Validators.required, Validators.email]],

      // role-based fields
      specialty: [''],
      yearsOfExperience: [''],
      dateOfBirth: [''],
      address: ['']
    });

    // ✅ Whenever role changes, update validators correctly
    this.registrationForm.get('role')?.valueChanges.subscribe((role: Role) => {
      this.onRoleChangeInternal(role);
    });

    // ✅ If email was marked duplicate, clear that error when user edits email again
    this.registrationForm.get('email')?.valueChanges.subscribe(() => {
      const emailCtrl = this.email;
      if (emailCtrl?.hasError('duplicate')) {
        const errs = { ...(emailCtrl.errors || {}) };
        delete errs['duplicate'];
        emailCtrl.setErrors(Object.keys(errs).length ? errs : null);
      }
      // also clear message as user is correcting it
      if (this.errorMessage) this.errorMessage = null;
    });
  }

  // ✅ Validator: prevent future DOB (also safe for empty values)
  noFutureDateValidator(control: AbstractControl) {
    if (!control.value) return null;
    const selected = new Date(control.value);
    const today = new Date();
    selected.setHours(0, 0, 0, 0);
    today.setHours(0, 0, 0, 0);

    if (selected > today) {
      return { futureDate: true };
    }
    return null;
  }

  // ✅ Helper: apply validators safely
  private applyValidators(controlName: string, validators: ValidatorFn[]): void {
    const ctrl = this.registrationForm.get(controlName);
    if (!ctrl) return;
    ctrl.setValidators(validators);
    ctrl.updateValueAndValidity({ emitEvent: false });
  }

  private clearValidators(controlName: string): void {
    const ctrl = this.registrationForm.get(controlName);
    if (!ctrl) return;
    ctrl.clearValidators();
    ctrl.updateValueAndValidity({ emitEvent: false });
  }

  private onRoleChangeInternal(role: Role): void {
    this.selectedRole = role;

    // Reset role-based fields when switching role
    this.specialty?.reset();
    this.yearsOfExperience?.reset();
    this.dateOfBirth?.reset();
    this.address?.reset();

    if (role === 'DOCTOR') {
      // Doctor required fields
      this.applyValidators('specialty', this.doctorSpecialtyValidators);
      this.applyValidators('yearsOfExperience', this.doctorYoeValidators);

      // Patient fields not required for Doctor
      this.clearValidators('dateOfBirth');
      this.clearValidators('address');
    }

    if (role === 'PATIENT') {
      // Patient required fields
      this.applyValidators('dateOfBirth', this.patientDobValidators);
      this.applyValidators('address', this.patientAddressValidators);

      // Doctor fields not required for Patient
      this.clearValidators('specialty');
      this.clearValidators('yearsOfExperience');
    }
  }

  onRoleChange(event: Event): void {
    const role = (event.target as HTMLSelectElement).value as Role;
    this.onRoleChangeInternal(role);
  }

  // ✅ "Already a user? Login" action (can be used in HTML)
  goToLogin(): void {
    this.router.navigate(['/auth/login']);
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.registrationForm.invalid) {
      this.registrationForm.markAllAsTouched();
      this.errorMessage = 'Please fill all fields correctly';
      return;
    }

    const formValue = this.registrationForm.value;

    // ✅ Base payload
    const payload: any = {
      username: formValue.username,
      password: formValue.password,
      role: formValue.role,
      fullName: formValue.fullName,
      contactNumber: formValue.contactNumber,
      email: formValue.email
    };

    // ✅ Add role-based fields
    if (formValue.role === 'DOCTOR') {
      payload.specialty = formValue.specialty || '';
      payload.yearsOfExperience = formValue.yearsOfExperience !== null && formValue.yearsOfExperience !== ''
        ? Number(formValue.yearsOfExperience)
        : 0;
    }

    if (formValue.role === 'PATIENT') {
      payload.dateOfBirth = formValue.dateOfBirth || null;
      payload.address = formValue.address || '';
    }

    console.log('Sending payload:', payload);

    this.authService.createUser(payload).subscribe({
      next: (res) => {
        console.log('User created:', res);

        // ✅ Show message and redirect to login
        this.successMessage = 'Registration successful! Redirecting to login...';
        this.errorMessage = null;

        // ✅ Reset form but keep success message until redirect
        this.resetForm(false);

        setTimeout(() => {
          this.router.navigate(['/auth/login']);
        }, 1200);
      },

      error: (err) => {
        console.error('Registration error:', err);

        const backendMsg =
          err?.error?.message ||
          err?.error?.error ||
          err?.error?.details ||
          err?.message;

        this.errorMessage = backendMsg || 'Registration failed. Please try again.';
        this.successMessage = null;

        // ✅ Mark email as duplicate if backend indicates email issue
        if (backendMsg && backendMsg.toLowerCase().includes('email')) {
          this.email?.setErrors({ ...(this.email.errors || {}), duplicate: true });
        }
      }
    });
  }

  // ✅ Reset form (option to clear messages)
  resetForm(clearMessages: boolean = true): void {
    this.registrationForm.reset();
    this.selectedRole = null;

    // clear validators on role-based fields after reset
    this.clearValidators('specialty');
    this.clearValidators('yearsOfExperience');
    this.clearValidators('dateOfBirth');
    this.clearValidators('address');

    if (clearMessages) {
      this.successMessage = null;
      this.errorMessage = null;
    }
  }

  // ✅ Getters
  get username() { return this.registrationForm.get('username'); }
  get password() { return this.registrationForm.get('password'); }
  get role() { return this.registrationForm.get('role'); }
  get fullName() { return this.registrationForm.get('fullName'); }
  get contactNumber() { return this.registrationForm.get('contactNumber'); }
  get email() { return this.registrationForm.get('email'); }

  get specialty() { return this.registrationForm.get('specialty'); }
  get yearsOfExperience() { return this.registrationForm.get('yearsOfExperience'); }
  get dateOfBirth() { return this.registrationForm.get('dateOfBirth'); }
  get address() { return this.registrationForm.get('address'); }
}