
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from '../../services/mediconnect.service';

@Component({
  selector: 'app-appointment-create',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentCreateComponent implements OnInit {
  appointmentForm!: FormGroup;
  patientId!: number;
  selectedPatient: any;
  clinics: any[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private fb: FormBuilder, private mediConnectService: MediConnectService) {}

  ngOnInit(): void {
    this.patientId = Number(localStorage.getItem('patient_id'));

    this.appointmentForm = this.fb.group({
      clinic: ['', Validators.required],
      appointmentDate: ['', Validators.required],
      status: ['', Validators.required],
      purpose: ['', Validators.required]
    });

    this.mediConnectService.getPatientById(this.patientId).subscribe((patient) => {
      this.selectedPatient = patient;
    });

    this.mediConnectService.getAllClinics().subscribe((clinics) => {
      this.clinics = clinics;
    });
  }

  onSubmit(): void {
    if (this.appointmentForm.valid && this.selectedPatient) {
      const payload = {
        ...this.appointmentForm.value,
        patient: this.selectedPatient
      };

      this.mediConnectService.createAppointment(payload).subscribe({
        next: (res: any) => {
          this.successMessage = res.message;
          this.errorMessage = null;
        },
        error: () => {
          this.successMessage = null;
          this.errorMessage = 'Failed to create appointment';
        }
      });
    }
  }
}
