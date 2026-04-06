// import { Component, OnInit, Output, EventEmitter } from '@angular/core';
// import { FormBuilder, FormGroup, Validators } from '@angular/forms';
// import { MediConnectService } from '../../services/mediconnect.service';
// import { Router } from '@angular/router';

// @Component({
//   selector: 'app-appointment-create',
//   templateUrl: './appointment.component.html',
//   styleUrls: ['./appointment.component.scss']
// })
// export class AppointmentCreateComponent implements OnInit {

//   @Output() booked = new EventEmitter<void>();

//   appointmentForm!: FormGroup;
//   patientId!: number;
//   selectedPatient: any;

//   clinics: any[] = [];
//   successMessage: string | null = null;
//   errorMessage: string | null = null;

//   // ✅ prevent past dates
//   today: string = new Date().toISOString().split('T')[0];

//   constructor(
//     private fb: FormBuilder,
//     private mediConnectService: MediConnectService,
//     private router: Router
//   ) {}

//   ngOnInit(): void {
//     this.patientId = Number(localStorage.getItem('patient_id'));

//     this.appointmentForm = this.fb.group({
//       clinic: ['', Validators.required],          // can be object OR id
//       appointmentDate: ['', Validators.required],
//       purpose: ['', Validators.required]
//     });

//     this.mediConnectService.getPatientById(this.patientId).subscribe({
//       next: patient => this.selectedPatient = patient,
//       error: () => this.selectedPatient = null
//     });

//     this.mediConnectService.getAllClinics().subscribe({
//       next: clinics => this.clinics = clinics || [],
//       error: () => this.clinics = []
//     });
//   }

//   onSubmit(): void {
//     this.successMessage = null;
//     this.errorMessage = null;

//     if (this.appointmentForm.invalid || !this.selectedPatient) {
//       this.appointmentForm.markAllAsTouched();
//       this.errorMessage = 'Please fill all fields';
//       return;
//     }

//     const raw = this.appointmentForm.value;

//     // ✅ SUPPORT BOTH OBJECT & ID
//     const clinicId =
//       typeof raw.clinic === 'object'
//         ? raw.clinic?.clinicId
//         : Number(raw.clinic);

//     // ✅ ✅ FINAL PAYLOAD (THIS FIXES THE ISSUE)
//     const payload: any = {
//       appointmentDate: raw.appointmentDate, // yyyy-MM-dd
//       purpose: raw.purpose,

//       // ✅ critical links
//       patientId: this.patientId,
//       clinicId: clinicId,

//       // ✅ optional but safe (many backends expect this)
//       patient: this.selectedPatient,
//       clinic: clinicId ? { clinicId } : raw.clinic
//     };

//     // console.log('Appointment payload:', payload);

//     this.mediConnectService.createAppointment(payload).subscribe({
//       next: res => {
//         this.successMessage = res?.message || 'Appointment created successfully';
//         this.errorMessage = null;

//         this.appointmentForm.reset();
//         this.booked.emit();

//         // ✅ FORCE DASHBOARD REFRESH
//         this.router.navigate(['/mediconnect/dashboard'], {
//           queryParams: { refresh: Date.now() }
//         });
//       },
//       error: () => {
//         this.successMessage = null;
//         this.errorMessage = 'Failed to create appointment';
//       }
//     });
//   }
// }

import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MediConnectService } from '../../services/mediconnect.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-appointment-create',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.scss']
})
export class AppointmentCreateComponent implements OnInit {

  @Output() booked = new EventEmitter<void>();

  appointmentForm!: FormGroup;
  patientId!: number;

  clinics: any[] = [];
  successMessage: string | null = null;
  errorMessage: string | null = null;

  today: string = new Date().toISOString().split('T')[0];

  constructor(
    private fb: FormBuilder,
    private mediConnectService: MediConnectService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.patientId = Number(localStorage.getItem('patient_id'));

    this.appointmentForm = this.fb.group({
      clinicId: ['', Validators.required],        // ✅ store clinicId
      appointmentDate: ['', Validators.required],
      purpose: ['', Validators.required]
    });

    this.mediConnectService.getAllClinics().subscribe({
      next: (clinics) => (this.clinics = clinics || []),
      error: () => (this.clinics = [])
    });
  }

  onSubmit(): void {
    this.successMessage = null;
    this.errorMessage = null;

    if (this.appointmentForm.invalid || !this.patientId) {
      this.appointmentForm.markAllAsTouched();
      this.errorMessage = 'Please fill all fields';
      return;
    }

    const raw = this.appointmentForm.value;

    const clinicId = Number(raw.clinicId);

    // ✅ ✅ BACKEND EXPECTS Appointment entity with patient + clinic objects
    const payload = {
      patient: { patientId: this.patientId },
      clinic: { clinicId: clinicId },
      appointmentDate: raw.appointmentDate, // yyyy-MM-dd
      purpose: raw.purpose,
      status: 'Scheduled' // ✅ avoids null issues
    };

    console.log('✅ Booking payload:', payload);

    this.mediConnectService.createAppointment(payload).subscribe({
      next: (res: any) => {
        this.successMessage = res?.message || 'Appointment created successfully';
        this.errorMessage = null;

        this.appointmentForm.reset();
        this.booked.emit();

        // ✅ Refresh dashboard view reliably
        this.router.navigate(['/mediconnect/dashboard'], {
          queryParams: { refresh: Date.now() }
        });
      },
      error: (err) => {
        console.error('❌ Booking failed:', err);

        const backendMsg =
          err?.error?.message ||
          err?.error?.error ||
          err?.error?.details ||
          err?.message;

        this.successMessage = null;
        this.errorMessage = backendMsg || 'Failed to create appointment';
      }
    });
  }
}