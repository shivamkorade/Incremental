import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({ providedIn: 'root' })
export class MediConnectService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  private getAuthHeaders() {
    const token = localStorage.getItem('token');
    return { headers: { Authorization: `Bearer ${token}` } };
  }

  // ---------------- PATIENT ----------------
  savePatient(patient: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/patient`, patient, this.getAuthHeaders());
  }
  getPatientById(patientId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/patient/${patientId}`, this.getAuthHeaders());
  }
  getPatientsByDoctorId(doctorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/appointment/patients/doctor/${doctorId}`);
  }
  getAllPatients(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/patient`, this.getAuthHeaders());
  }
  updatePatient(patientId: number, data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/patient/${patientId}`, data, this.getAuthHeaders());
  }
  deletePatient(patientId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/patient/${patientId}`, this.getAuthHeaders());
  }

  // ---------------- DOCTOR ----------------
  saveDoctor(doctor: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/doctor`, doctor, this.getAuthHeaders());
  }
  getDoctorById(doctorId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/doctor/${doctorId}`, this.getAuthHeaders());
  }
  getAllDoctors(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/doctor`, this.getAuthHeaders());
  }
  updateDoctor(doctorId: number, doctor: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/doctor/${doctorId}`, doctor, this.getAuthHeaders());
  }
  deleteDoctor(doctorId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/doctor/${doctorId}`, this.getAuthHeaders());
  }
  getUserById(userId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/user/${userId}`, this.getAuthHeaders());
  }

  // ---------------- CLINIC ----------------
  getAllClinics(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/clinic`, this.getAuthHeaders());
  }
  addClinic(clinic: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/clinic`, clinic, this.getAuthHeaders());
  }
  updateClinic(clinicId: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/clinic/${clinicId}`, data, this.getAuthHeaders());
  }
  deleteClinic(clinicId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/clinic/${clinicId}`, this.getAuthHeaders());
  }
  getClinicsByDoctorId(doctorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/clinic/doctor/${doctorId}`, this.getAuthHeaders());
  }
  getClinicsByLocation(location: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/clinic/location/${location}`, this.getAuthHeaders());
  }

  // ---------------- APPOINTMENT ----------------
  getAllAppointments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/appointment`, this.getAuthHeaders());
  }
  getAppointmentById(appointmentId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/appointment/${appointmentId}`, this.getAuthHeaders());
  }
  getAppointmentsByPatient(patientId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/appointment/patient/${patientId}`, this.getAuthHeaders());
  }
  getAppointmentsByClinic(clinicId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/appointment/clinic/${clinicId}`, this.getAuthHeaders());
  }
  getAppointmentsByStatus(status: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/appointment/status/${status}`, this.getAuthHeaders());
  }
  getAppointmentsByDoctorId(doctorId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/appointment`, this.getAuthHeaders());
  }
  createAppointment(data: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/appointment`, data, this.getAuthHeaders());
  }
  updateAppointment(data: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/appointment/${data.appointmentId}`, data, this.getAuthHeaders());
  }
  approveAppointment(appointmentId: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/appointment/${appointmentId}/approve`, {}, this.getAuthHeaders());
  }
  rejectAppointment(appointmentId: number): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/appointment/${appointmentId}/reject`, {}, this.getAuthHeaders());
  }
  addDoctorNotes(appointmentId: number, notes: string): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/appointment/${appointmentId}/notes`, { doctorNotes: notes }, this.getAuthHeaders());
  }
  deleteAppointment(appointmentId: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/appointment/${appointmentId}`, this.getAuthHeaders());
  }

  // ---------------- BILLING ----------------
  createBill(billing: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/billing`, billing, this.getAuthHeaders());
  }
  getBillsByPatient(patientId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/billing/patient/${patientId}`, this.getAuthHeaders());
  }
  getBillByAppointment(appointmentId: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/billing/appointment/${appointmentId}`, this.getAuthHeaders());
  }
  payBill(billingId: number, billing: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/billing/${billingId}`, billing, this.getAuthHeaders());
  }
}