import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'app-appointment-create',
    templateUrl: './appointment.component.html',
    styleUrls: ['./appointment.component.scss']
})
export class AppointmentCreateComponent implements OnInit {

    appointmentForm!: FormGroup;

    successMessage: string | null = null;
    errorMessage: string | null = null;

    constructor(private fb: FormBuilder) { }

    ngOnInit(): void {
        this.appointmentForm = this.fb.group({
            appointmentId: [null, [Validators.required, Validators.min(1)]],
            patientId: [null, [Validators.required, Validators.min(1)]],
            clinicId: [null, [Validators.required, Validators.min(1)]],
            appointmentDate: ['', [Validators.required]],
            status: ['', [Validators.required]],
            purpose: ['', [Validators.required, Validators.minLength(5)]],
        });
    }

    // Convenience getter to use in template
    get f() {
        return this.appointmentForm.controls;
    }

    onSubmit(): void {
        this.successMessage = null;
        this.errorMessage = null;

        if (this.appointmentForm.invalid) {
            // Mark all controls touched to show validation messages
            this.appointmentForm.markAllAsTouched();
            this.errorMessage = 'Please correct the errors in the form before submitting.';
            return;
        }

        // Simulate a successful save (no HttpClient/service here to keep unit tests simple)
        this.successMessage = 'Appointment saved successfully!';
    }


    resetForm(): void {
        this.appointmentForm.reset({
            appointmentId: null,
            patientId: null,
            clinicId: null,
            appointmentDate: '',
            status: '',
            purpose: ''
        });
    }
}