export class DoctorDTO {
    doctorld: number;
    username: string;
    password: string;
    fullName: string;
    contactNumber: string;
    email: string;
    specialty: string;
    yearsOfExperience: number;

    constructor(
        doctorld: number,
        username: string,
        password: string,
        fullName: string,
        contactNumber: string,
        email: string,
        specialty: string,
        yearsOfExperience: number
    ) {
        this.doctorld = doctorld;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.specialty = specialty;
        this.yearsOfExperience = yearsOfExperience;
    }

    logAttributes(): void {
        console.log("doctorld: " + this.doctorld);
        console.log("username: " + this.username);
        console.log("password: " + this.password);
        console.log("fullName: " + this.fullName);
        console.log("contactNumber: " + this.contactNumber);
        console.log("email: " + this.email);
        console.log("specialty: " + this.specialty);
        console.log("yearsOfExperience: " + this.yearsOfExperience);
    }
}
