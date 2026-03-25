import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MediConnectService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // ✅ Add token headers here
  private getAuthHeaders() {
    const token = localStorage.getItem("token");
    return {
      headers: {
        Authorization: `Bearer ${token}`
      }
    };
  }

  // ---------------- PATIENT & DOCTOR CREATION ----------------

  savePatient(patient: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/patients`, patient, this.getAuthHeaders());
  }

  saveDoctor(doctor: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/doctor`, doctor, this.getAuthHeaders());
  }

  // ---------------- PATIENT ----------------

  getPatientById(patientId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/patients/${patientId}`, this.getAuthHeaders());
  }

  updatePatient(patientId: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/patients/${patientId}`, data, this.getAuthHeaders());
  }

  deletePatient(patientId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/patients/${patientId}`, this.getAuthHeaders());
  }

  // ---------------- DOCTOR ----------------

  // ✅ FIXED PATH (/doctor NOT /doctors)
  getDoctorById(doctorId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/doctor/${doctorId}`, this.getAuthHeaders());
  }

  // ✅ FIXED path
  updateDoctor(doctorId: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/doctor/${doctorId}`, data, this.getAuthHeaders());
  }

  // ✅ FIXED path
  deleteDoctor(doctorId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/doctor/${doctorId}`, this.getAuthHeaders());
  }

  getUserById(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/users/${userId}`, this.getAuthHeaders());
  }

  // ---------------- CLINIC ----------------

  getAllClinics(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/clinics`, this.getAuthHeaders());
  }

  addClinic(clinic: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/clinics`, clinic, this.getAuthHeaders());
  }

  updateClinic(clinicId: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/clinics/${clinicId}`, data, this.getAuthHeaders());
  }

  deleteClinic(clinicId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/clinics/${clinicId}`, this.getAuthHeaders());
  }

  // ✅ FIXED path: /doctor/{id}/clinics
  getClinicsByDoctorId(doctorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/doctor/${doctorId}/clinics`, this.getAuthHeaders());
  }

  // ---------------- APPOINTMENT ----------------

  getAppointmentsByPatient(patientId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/appointments/patient/${patientId}`, this.getAuthHeaders());
  }

  getAppointmentsByClinic(clinicId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/clinics/${clinicId}/appointments`, this.getAuthHeaders());
  }

  // ✅ FIXED path
  getAppointmentsByDoctorId(doctorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/appointments/doctor/${doctorId}`, this.getAuthHeaders());
  }

  createAppointment(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/appointments`, data, this.getAuthHeaders());
  }

  updateAppointment(data: any): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/appointments/${data.appointmentId}`,
      data,
      this.getAuthHeaders()
    );
  }
}