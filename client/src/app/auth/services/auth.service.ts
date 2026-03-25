import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/mediconnect/models/User';

export interface UserRegistrationDTO {
  username: string;
  password: string;
  role: string;
  fullName: string;
  contactNumber: string;
  email: string;
  specialty?: string;
  yearsOfExperience?: number;
  dateOfBirth?: Date;
  address?: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private loginUrl = `${environment.apiUrl}/user/login`;
  private registerUrl = `${environment.apiUrl}/user/register`;

  constructor(private http: HttpClient) {}

  private getAuthHeaders() {
    const token = localStorage.getItem('token');
    return {
      headers: {
        Authorization: `Bearer ${token}`
      }
    };
  }

  login(user: Partial<User>): Observable<{ [key: string]: string }> {
    return this.http.post<{ [key: string]: string }>(this.loginUrl, user);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  logout(): void {
    localStorage.clear();
  }

  createUser(user: UserRegistrationDTO): Observable<any> {
    return this.http.post<any>(this.registerUrl, user);
  }

  storeLoginData(res: any): void {
    console.log('Login response:', res);

    // Always store token and userId
    if (res.token) {
      localStorage.setItem('token', res.token);
    }
    if (res.userId) {
      localStorage.setItem('user_id', res.userId.toString());
    }

    // ✅ FIXED: Set role based on response
    if (res.roles) {
      localStorage.setItem('role', res.roles);
    }

    // ✅ FIXED: Store doctor_id and set role DOCTOR
    if (res.doctorId) {
      localStorage.setItem('doctor_id', res.doctorId.toString());
      // Also store as userId so dashboard can read it
      localStorage.setItem('userId', res.doctorId.toString());
      if (!res.patientId) {
        localStorage.setItem('role', 'DOCTOR');
      }
    }

    // ✅ FIXED: Store patient_id and set role PATIENT
    if (res.patientId) {
      localStorage.setItem('patient_id', res.patientId.toString());
      // Also store as userId so dashboard can read it
      localStorage.setItem('userId', res.patientId.toString());
      localStorage.setItem('role', 'PATIENT');
    }

    console.log('Stored role:', localStorage.getItem('role'));
    console.log('Stored userId:', localStorage.getItem('userId'));
  }
}