import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/mediconnect/models/User';
// import { UserRegistrationDTO } from './dto/user-registration-dto';

export interface UserRegistrationDTO {
  username: string;
  password: string;
  role: string;
  fullName: string;
  contactNumber: string;
  email: string;
}
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loginUrl = `${environment.apiUrl}/user/login`;
  private registerUrl = `${environment.apiUrl}/user/register`;

  constructor(private http: HttpClient) {}

  private getAuthHeaders() {
  const token = localStorage.getItem("token");
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
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('user_id');
    localStorage.removeItem('doctor_id');
    localStorage.removeItem('patient_id');
  }

  createUser(user: UserRegistrationDTO): Observable<any> {
    return this.http.post<any>(this.registerUrl, user);
  }
  storeLoginData(res: any) {

  // ✅ Doctor Login
  if (res.doctorId) {
    localStorage.setItem("role", "DOCTOR");
    localStorage.setItem("userId", res.doctorId.toString());
    return;
  }

  // ✅ Patient Login
  if (res.patientId) {
    localStorage.setItem("role", "PATIENT");
    localStorage.setItem("userId", res.patientId.toString());
    return;
  }

  // ✅ Fallback (if backend sends generic id + role)
  if (res.role && res.id) {
    localStorage.setItem("role", res.role);
    localStorage.setItem("userId", res.id.toString());
    return;
  }

  console.error("Login response missing required fields:", res);


}

}