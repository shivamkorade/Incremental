import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
  selector: 'app-appointment-create',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentCreateComponent implements OnInit {
  @Output() booked = new EventEmitter<void>(); // ✅ notify dashboard

  appointmentForm!: FormGroup;
  patientId!: number;
  selectedPatient: any;
  clinics: any[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;

  // ✅ ADD: used to disable past dates in date picker (yyyy-MM-dd)
  today: string = new Date().toISOString().split('T')[0];

  constructor(private fb: FormBuilder, private mediConnectService: MediConnectService) {}

  ngOnInit(): void {
    this.patientId = Number(localStorage.getItem('patient_id'));

    this.appointmentForm = this.fb.group({
      clinic: ['', Validators.required],
      appointmentDate: ['', Validators.required],
      // status: ['', Validators.required],
      purpose: ['', Validators.required]
    });

    this.mediConnectService.getPatientById(this.patientId).subscribe({
      next: (patient) => (this.selectedPatient = patient),
      error: () => (this.selectedPatient = null)
    });

    this.mediConnectService.getAllClinics().subscribe({
      next: (clinics) => (this.clinics = clinics),
      error: () => (this.clinics = [])
    });
  }

  onSubmit(): void {
    if (this.appointmentForm.valid && this.selectedPatient) {
      const raw = this.appointmentForm.value;

      // ✅ keep date as "yyyy-MM-dd" string (do not convert to JS Date object)
      const payload = {
        ...raw,
        appointmentDate: raw.appointmentDate,
        patient: this.selectedPatient
      };

      this.mediConnectService.createAppointment(payload).subscribe({
        next: (res) => {
          this.successMessage = res?.message || 'Appointment created successfully';
          this.errorMessage = null;

          // ✅ clear form (optional)
          this.appointmentForm.reset();

          // ✅ notify dashboard to refresh list + close modal
          this.booked.emit();
        },
        error: () => {
          this.successMessage = null;
          this.errorMessage = 'Failed to create appointment';
        }
      });
    } else {
      this.successMessage = null;
      this.errorMessage = 'Please fill all fields';
    }
  }
}