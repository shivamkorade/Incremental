

// src/app/shared/shared.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';  // ✅ Corrected import
import { AuthModule } from '../auth/auth.module';

@NgModule({
  declarations: [
    NavbarComponent   // ✅ Correct class name
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule,
    AuthModule
  ],
  exports: [
    NavbarComponent   // ✅ Export correct class
  ]
})
export class SharedModule {}
