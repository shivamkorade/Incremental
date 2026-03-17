import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm!: FormGroup;
    errorMessage: string | null = null;
    successMessage: string | null = null;

    constructor(private formBuilder: FormBuilder,private authService: AuthService ) { }

    ngOnInit(): void {
        this.loginForm = this.formBuilder.group({
            username: ['', [Validators.required, Validators.pattern(/^[a-zA-Z0-9]+$/)]],
            password: [
                '',
                [
                    Validators.required,
                    Validators.minLength(8),
                    // at least one uppercase and one digit
                    Validators.pattern(/^(?=.*[A-Z])(?=.*\d).+$/)
                ]
            ],
        });
    }

    onSubmit(): void {
        // clear messages each submit
        this.successMessage = null;
        this.errorMessage = null;

        if (this.loginForm.invalid) {
            this.loginForm.markAllAsTouched();
            this.errorMessage = 'Please fill out all fields correctly.';
            return;
        }

        // In real app: call auth service and propagate backend errors to UI
        // this.authService.login(this.loginForm.value).subscribe({
        //   next: () => {
        //     this.successMessage = 'Login successful!';
        //     this.errorMessage = null;
        //   },
        //   error: (err) => {
        //     this.successMessage = null;
        //     // Properly propagate backend error messages to UI
        //     this.errorMessage = err?.error?.message || err?.message || 'Login failed. Please try again.';
        //   }
        // });

        // For tests / no-backend scenario:
        // this.successMessage = 'Login successful!';
        // this.errorMessage = null;
        this.authService.login(this.loginForm.value).subscribe(() => {
    this.successMessage = 'Login successful!';
    this.errorMessage = null;
});
    }

    // Convenience getters for template
    get username() { return this.loginForm.get('username'); }
    get password() { return this.loginForm.get('password'); }
}