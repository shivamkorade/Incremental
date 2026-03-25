import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { DashboardComponent } from "./components/dashboard/dashboard.component";
import { DoctorEditComponent } from "./components/doctoredit/doctoredit.component";
import { PatientEditComponent } from "./components/patientedit/patientedit.component";
import { ClinicCreateComponent } from "./components/cliniccreate/cliniccreate.component";
import { ClinicEditComponent } from "./components/clinicedit/clinicedit.component";
import { AppointmentCreateComponent } from "./components/appointment/appointment.component";
import { DoctorCreateComponent } from "./components/doctorcreate/doctorcreate.component";
import { PatientCreateComponent } from "./components/patientcreate/patientcreate.component";

const routes: Routes = [
  { path: 'dashboard',          component: DashboardComponent },
  { path: 'doctor/edit/:id',    component: DoctorEditComponent },
  { path: 'patient/edit/:id',   component: PatientEditComponent },
  { path: 'clinic/create',      component: ClinicCreateComponent },
  { path: 'clinic/edit/:id',    component: ClinicEditComponent },
  { path: 'appointment/create', component: AppointmentCreateComponent },
  { path: 'doctor/create',      component: DoctorCreateComponent },
  { path: 'patient/create',     component: PatientCreateComponent },
  { path: '',                   redirectTo: 'dashboard', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MediConnectRoutingModule {}