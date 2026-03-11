

import { Appointment } from './Appointment';
import { Doctor } from './Doctor';
import { User } from './User';

export class Clinic {
  constructor(
    public clinicId: number,
    public clinicName: string,
    public location: string,
    public doctorId: number,
    public contactNumber: string,
    public establishedYear: number
  ) {}

  logAttributes(): void {
    console.log('clinicId:', this.clinicId);
    console.log('clinicName:', this.clinicName);
    console.log('location:', this.location);
    console.log('doctorId:', this.doctorId);
    console.log('contactNumber:', this.contactNumber);
    console.log('establishedYear:', this.establishedYear);
  }
}
``