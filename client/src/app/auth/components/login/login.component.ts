import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm!: FormGroup;
    errorMessage: string | null = null;
    successMessage: string | null = null;

    constructor(private formBuilder: FormBuilder,private authService: AuthService,private router:Router ) { }

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
    this.successMessage = null;
    this.errorMessage = null;

    if (this.loginForm.invalid) {
        this.loginForm.markAllAsTouched();
        this.errorMessage = 'Please fill out all fields correctly.';
        return;
    }

    this.authService.login(this.loginForm.value).subscribe({

        next: (res: any) => {
            console.log('Login Response:', res);

            // ✅ STORE DATA
            this.authService.storeLoginData(res);

            // ✅ SUCCESS MESSAGE
            this.successMessage = 'Login successful!';
            this.errorMessage = null;

            // ✅ REDIRECT (IMPORTANT)
            setTimeout(() => {
                this.router.navigate(['mediconnect/dashboard']);
            }, 500);
        },

        error: (err) => {
            console.error('Login Error:', err);

            this.successMessage = null;

            // ✅ Proper backend error handling
            if (err.status === 401) {
                this.errorMessage = 'Invalid username or password';
            } else {
                this.errorMessage =
                    err?.error?.message ||
                    'Something went wrong. Please try again.';
            }
        }

    });
}

    // Convenience getters for template
    get username() { return this.loginForm.get('username'); }
    get password() { return this.loginForm.get('password'); }
}