import { NgModule } from '@angular/core';

import { CommonModule } from '@angular/common';

import { RouterModule, Routes } from '@angular/router';

import { HttpClientModule } from '@angular/common/http';

// Minimal placeholder components you can replace with real ones

import { Component } from '@angular/core';

import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { PatientCreateComponent } from './components/patientcreate/patientcreate.component';

import { DoctorCreateComponent } from './components/doctorcreate/doctorcreate.component';

import { ClinicCreateComponent } from './components/cliniccreate/cliniccreate.component';

// import { ClinicCreateComponent } from './clinic/cliniccreate.component'; 

@Component({

  selector: 'mc-dashboard',

  template: `<h3>MediConnect Dashboard</h3><p>Welcome to MediConnect</p>`

})

export class DashboardComponent { }

@Component({

  selector: 'mc-doctors',

  template: `<h3>Doctors</h3>`

})

export class DoctorsComponent { }

@Component({

  selector: 'mc-clinics',

  template: `<h3>Clinics</h3>`

})

export class ClinicsComponent { }

@Component({

  selector: 'mc-appointments',

  template: `<h3>Appointments</h3>`

})

export class AppointmentsComponent { }

const routes: Routes = [

  { path: '', component: DashboardComponent },

  { path: 'doctors', component: DoctorsComponent },

  { path: 'clinics', component: ClinicsComponent },

  // { path: 'patients', component: PatientsComponent },

  // { path: 'appointments', component: AppointmentsComponent },

];

@NgModule({

  declarations: [

    ClinicCreateComponent,

    // DashboardComponent,

    // DoctorsComponent,

    // ClinicsComponent,

    AppointmentsComponent,

    PatientCreateComponent,

    DoctorCreateComponent

  ],

  imports: [CommonModule, HttpClientModule, ReactiveFormsModule, FormsModule],

})

export class MediConnectModule { }
