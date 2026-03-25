import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

// ✅ Import the routing module (contains all correct routes)
import { MediConnectRoutingModule } from './mediconnect-routing.module';

// ✅ All components declared
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DoctorEditComponent } from './components/doctoredit/doctoredit.component';
import { PatientEditComponent } from './components/patientedit/patientedit.component';
import { ClinicCreateComponent } from './components/cliniccreate/cliniccreate.component';
import { ClinicEditComponent } from './components/clinicedit/clinicedit.component';
import { AppointmentCreateComponent } from './components/appointment/appointment.component';
import { DoctorCreateComponent } from './components/doctorcreate/doctorcreate.component';
import { PatientCreateComponent } from './components/patientcreate/patientcreate.component';

@NgModule({
  declarations: [
    DashboardComponent,
    DoctorEditComponent,
    PatientEditComponent,
    ClinicCreateComponent,
    ClinicEditComponent,
    AppointmentCreateComponent,
    DoctorCreateComponent,
    PatientCreateComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule,
    RouterModule,
    MediConnectRoutingModule  // ✅ uses routes from routing module, no inline routes here
  ]
})
export class MediConnectModule {}