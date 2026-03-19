
// import { NgModule } from '@angular/core'; 
// import { CommonModule } from '@angular/common'; 
// import { ReactiveFormsModule, FormsModule } from '@angular/forms'; 
// import { RouterModule } from '@angular/router'; 
// import { HttpClientModule } from '@angular/common/http'; 
// import { DashboardComponent } from './components/dashboard/dashboard.component'; 
// import { ClinicCreateComponent } from './components/cliniccreate/cliniccreate.component'; 
// import { AppointmentCreateComponent } from './components/appointment/appointment.component'; 
// import { PatientEditComponent } from './components/patientedit/patientedit.component'; 
// import { DoctorEditComponent } from './components/doctoredit/doctoredit.component'; 
// import { ClinicEditComponent } from './components/clinicedit/clinicedit.component'; 
// import { MediconnectRoutingModule } from './mediconnect-routing.module'; 
// import { PatientCreateComponent } from './components/patientcreate/patientcreate.component'; 
// import { DoctorCreateComponent } from './components/doctorcreate/doctorcreate.component'; 
// @NgModule({ 
//   declarations: [ 
//     DashboardComponent, 
//     PatientCreateComponent, 
//     DoctorCreateComponent, 
//     ClinicCreateComponent, 
//     AppointmentCreateComponent, 
//     PatientEditComponent, 
//     DoctorEditComponent, 
//     ClinicEditComponent 
//   ], 
//   imports: [ 
//     CommonModule, 
//     FormsModule, 
//     ReactiveFormsModule, 
//     RouterModule, 
//     HttpClientModule, 
//     MediconnectRoutingModule 
//   ], 
//   exports: [ 
//     DashboardComponent, 
//     ClinicCreateComponent, 
//     AppointmentCreateComponent, 
//     PatientEditComponent, 
//     DoctorEditComponent, 
//     ClinicEditComponent 
//   ] 
// }) 
//   export class MediConnectModule {}





import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

// Minimal placeholder components you can replace with real ones
import { Component } from '@angular/core';

@Component({
  selector: 'mc-dashboard',
  template: `<h3>MediConnect Dashboard</h3><p>Welcome to MediConnect</p>`
})
export class DashboardComponent {}

@Component({
  selector: 'mc-doctors',
  template: `<h3>Doctors</h3>`
})
export class DoctorsComponent {}

@Component({
  selector: 'mc-clinics',
  template: `<h3>Clinics</h3>`
})
export class ClinicsComponent {}

@Component({
  selector: 'mc-appointments',
  template: `<h3>Appointments</h3>`
})
export class AppointmentsComponent {}

const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'doctors', component: DoctorsComponent },
  { path: 'clinics', component: ClinicsComponent },
  { path: 'appointments', component: AppointmentsComponent },
];

@NgModule({
  declarations: [
    DashboardComponent,
    DoctorsComponent,
    ClinicsComponent,
    AppointmentsComponent
  ],
  imports: [CommonModule, RouterModule.forChild(routes), HttpClientModule],
})
export class MediConnectModule {}
