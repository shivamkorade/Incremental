import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DoctorEditComponent } from './components/doctoredit/doctoredit.component';
import { ClinicEditComponent } from './components/clinicedit/clinicedit.component';

const routes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'doctor-edit', component: DoctorEditComponent },
  { path: 'clinic-edit/:id', component: ClinicEditComponent }
];

@NgModule({
  declarations: [
    DashboardComponent,
    DoctorEditComponent,
    ClinicEditComponent
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    ReactiveFormsModule,
    RouterModule.forChild(routes)
  ]
})
export class MediConnectModule {}