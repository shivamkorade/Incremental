import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

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

  constructor(private formBuilder: FormBuilder) { }

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
      fullName: ['', [Validators.required]],
      contactNumber: ['', [Validators.required, Validators.pattern(/^\d{10}$/)]],
      email: ['', [Validators.required, Validators.email]],

      // Doctor-specific
      specialty: [''],
      yearsOfExperience: [''],

      // Patient-specific
      dateOfBirth: [''],
      address: ['']
    });

    // Wire-up dynamic validators on role change
    this.registrationForm.get('role')?.valueChanges.subscribe((role: Role) => {
      this.onRoleChangeInternal(role);
    });
  }

  private setRequired(ctrl: AbstractControl | null, required: boolean): void {
    if (!ctrl) return;
    const validators = ctrl.validator ? [ctrl.validator] : [];
    // Remove any existing required
    const filtered = validators.filter((v: any) => v !== Validators.required);
    if (required) {
      filtered.push(Validators.required);
    }
    ctrl.setValidators(filtered);
    ctrl.updateValueAndValidity({ emitEvent: false });
  }

  private onRoleChangeInternal(role: Role): void {
    this.selectedRole = role;

    const specialty = this.registrationForm.get('specialty');
    const yoe = this.registrationForm.get('yearsOfExperience');
    const dob = this.registrationForm.get('dateOfBirth');
    const address = this.registrationForm.get('address');

    // Clear values and validators before applying role-specific rules
    specialty?.reset();
    yoe?.reset();
    dob?.reset();
    address?.reset();

    // Reset base validators for role-specific fields
    yoe?.setValidators([Validators.min(0)]); // baseline for number if set

    // Apply role-specific required validators
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
    } else {
      // No role
      this.setRequired(specialty, false);
      this.setRequired(yoe, false);
      this.setRequired(dob, false);
      this.setRequired(address, false);
    }

    specialty?.updateValueAndValidity({ emitEvent: false });
    yoe?.updateValueAndValidity({ emitEvent: false });
    dob?.updateValueAndValidity({ emitEvent: false });
    address?.updateValueAndValidity({ emitEvent: false });
  }

  onRoleChange(event: Event): void {
    const select = event.target as HTMLSelectElement;
    const role = (select.value || '') as Role;
    this.onRoleChangeInternal(role);
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.registrationForm.invalid) {
      this.registrationForm.markAllAsTouched();
      // this.errorMessage = 'Please fill out all required fields correctly.';
      this.errorMessage = 'Please fill out all fields correctly.';
      return;
    }

    const payload = this.registrationForm.value;

    // In a real app: call auth/register API and propagate backend errors
    // this.authService.register(payload).subscribe({
    //   next: () => {
    //     this.successMessage = 'Registration successful!';
    //     this.errorMessage = null;
    //     this.resetForm();
    //   },
    //   error: (err) => {
    //     this.successMessage = null;
    //     this.errorMessage = err?.error?.message || err?.message || 'Registration failed. Please try again.';
    //   }
    // });

    // For tests / no-backend scenario:
    this.successMessage = 'Registration successful!';
    this.errorMessage = null;
    this.resetForm();
  }

  resetForm(): void {
    this.registrationForm.reset();
    this.selectedRole = null;
  }

  // Convenience getters
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