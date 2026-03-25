import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

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

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService
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

      // optional
      specialty: [''],
      yearsOfExperience: [''],
      dateOfBirth: ['', [Validators.required, this.noFutureDateValidator]],
      address: ['']
    });

    this.registrationForm.get('role')?.valueChanges.subscribe((role: Role) => {
      this.onRoleChangeInternal(role);
    });
  }

  noFutureDateValidator(control: AbstractControl) {
  const selected = new Date(control.value);
  const today = new Date();
  if (selected > today) {
    return { futureDate: true };
  }
  return null;
}

  private setRequired(ctrl: AbstractControl | null, required: boolean): void {
    if (!ctrl) return;
    ctrl.setValidators(required ? [Validators.required] : []);
    ctrl.updateValueAndValidity({ emitEvent: false });
  }

  private onRoleChangeInternal(role: Role): void {
    this.selectedRole = role;

    const specialty = this.registrationForm.get('specialty');
    const yoe = this.registrationForm.get('yearsOfExperience');
    const dob = this.registrationForm.get('dateOfBirth');
    const address = this.registrationForm.get('address');

    specialty?.reset();
    yoe?.reset();
    dob?.reset();
    address?.reset();

    if (role === 'DOCTOR') {
      this.setRequired(specialty, true);
      this.setRequired(yoe, true);
      this.setRequired(dob, false);
      this.setRequired(address, false);
    } else if (role === 'PATIENT') {
      this.setRequired(specialty, false);
      this.setRequired(yoe, false);
      this.setRequired(dob, true);
      this.setRequired(address, true);
    }
  }

  onRoleChange(event: Event): void {
    const role = (event.target as HTMLSelectElement).value as Role;
    this.onRoleChangeInternal(role);
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

  let payload: any = {
    username: formValue.username,
    password: formValue.password,
    role: formValue.role,
    fullName: formValue.fullName,
    contactNumber: formValue.contactNumber,
    email: formValue.email
  };

  // ✅ ADD ROLE-BASED FIELDS (CRITICAL FIX)
  if (formValue.role === 'DOCTOR') {
    payload.specialty = formValue.specialty || '';
    payload.yearsOfExperience = formValue.yearsOfExperience
      ? Number(formValue.yearsOfExperience)
      : 0;   // 🔥 prevents null crash
  }

  if (formValue.role === 'PATIENT') {
    payload.dateOfBirth = formValue.dateOfBirth || null;
    payload.address = formValue.address || '';
  }

  console.log('Sending payload:', payload); // ✅ DEBUG

  this.authService.createUser(payload).subscribe({
    next: (res) => {
      console.log('User created:', res);
      this.successMessage = 'Registration successful!';
      this.resetForm();
    },
    error: (err) => {
  console.error('Registration error:', err);

  // Extract backend message safely
  const backendMsg =
    err?.error?.message ||    // Spring Boot @ExceptionHandler returns here
    err?.error?.error ||      // Some APIs return { error: "message" }
    err?.error?.details ||    // Validation frameworks use this
    err?.message;             // Fallback

  this.errorMessage = backendMsg || 'Registration failed. Please try again.';

  // OPTIONAL: Mark email field as duplicate
  if (backendMsg && backendMsg.toLowerCase().includes('email')) {
    this.email?.setErrors({ duplicate: true });
  }
}

  });
}

  resetForm(): void {
    this.registrationForm.reset();
    this.selectedRole = null;
  }

  // ✅ ALL REQUIRED GETTERS (fixes your error)
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