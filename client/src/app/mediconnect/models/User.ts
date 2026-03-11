
export class User {
    constructor(
      public userId: number,
      public username: string,
      public password: string,
      public role: string,
      public doctor?: {
        doctorId: number;
        fullName: string;
        email: string;
        specialty: string;
        yearsOfExperience: number;
        contactNumber: string;
        logAttributes(): void;
      }
    ) {}
  }
  