export class Doctor {
  constructor(
    public doctorId: number,
    public fullName: string,
    public contactNumber: string,
    public email: string,
    public specialty: string,
    public yearsOfExperience: number
  ) {}

  logAttributes(): void {
    console.log('doctorId:', this.doctorId);
    console.log('fullName:', this.fullName);
    console.log('contactNumber:', this.contactNumber);
    console.log('email:', this.email);
    console.log('specialty:', this.specialty);
    console.log('yearsOfExperience:', this.yearsOfExperience);
  }
}
