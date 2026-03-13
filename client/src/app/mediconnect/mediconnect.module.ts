import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { PatientCreateComponent } from './components/patientcreate/patientcreate.component';
import { DoctorArrayComponent } from './components/doctorarray/doctorarray.component';

@NgModule({
  declarations: [
    PatientCreateComponent,
    DoctorArrayComponent
  ],
  imports: [
    CommonModule,
    FormsModule,          // ngModel + template-driven forms
    ReactiveFormsModule,  // available for other components if needed
    HttpClientModule
  ],
  exports: [
    PatientCreateComponent,
    DoctorArrayComponent
  ]
})
export class MediconnectModule {}