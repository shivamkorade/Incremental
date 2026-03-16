import { Injectable } from "@angular/core";

import { environment } from "src/environments/environment";

import { HttpClient } from "@angular/common/http";

import { Observable } from "rxjs";

import { Patient } from "../models/Patient";

import { Doctor } from "../models/Doctor";

import { Clinic } from "../models/Clinic";

import { Appointment } from "../models/Appointment";

import { User } from "../models/User";

import { DoctorDTO } from "../models/DoctorDTO";

import { PatientDTO } from "../models/PatientDTO";

@Injectable({

  providedIn: "root",

})

export class MediConnectService {

  private baseUrl = environment.apiUrl; // e.g. 'http://localhost:8080/api'

  // ---- Resource path segments (adjust if your backend differs) ----

  private readonly PATIENTS = "patients";

  private readonly DOCTORS = "doctors";

  private readonly CLINICS = "clinics";

  private readonly APPOINTMENTS = "appointments";

  private readonly USERS = "users";

  constructor(private http: HttpClient) { }

  /** Safe URL joiner to avoid double slashes */

  private url(path: string): string {

    const root = String(this.baseUrl || "").replace(/\/+$/, "");

    const sub = path.replace(/^\/+/, "");

    return `${root}/${sub}`;

  }

  // ===================== PATIENT =====================

  addPatient(patient: Patient): Observable<Patient> {

    return this.http.post<Patient>(this.url(`${this.PATIENTS}`), patient);

  }

  // If your API requires /patients/{id}, ensure PatientDTO includes `id`

  updatePatient(patient: PatientDTO): Observable<Patient> {

    // Option A (common in many assignments): PUT /patients with body

    return this.http.put<Patient>(this.url(`${this.PATIENTS}`), patient);

    // Option B (uncomment if your backend expects path param):

    // return this.http.put<Patient>(this.url(`${this.PATIENTS}/${patient.id}`), patient);

  }

  deletePatient(patientId: number): Observable<any> {

    return this.http.delete<any>(this.url(`${this.PATIENTS}/${patientId}`));

  }

  getAllPatients(): Observable<Patient[]> {

    return this.http.get<Patient[]>(this.url(`${this.PATIENTS}`));

  }

  getPatientById(patientId: number): Observable<Patient> {

    return this.http.get<Patient>(this.url(`${this.PATIENTS}/${patientId}`));

  }

  // ===================== DOCTOR =====================

  addDoctor(doctor: Doctor): Observable<Doctor> {

    return this.http.post<Doctor>(this.url(`${this.DOCTORS}`), doctor);

  }

  updateDoctor(doctor: DoctorDTO): Observable<Doctor> {

    // Option A: PUT /doctors with body

    return this.http.put<Doctor>(this.url(`${this.DOCTORS}`), doctor);

    // Option B (path-param style):

    // return this.http.put<Doctor>(this.url(`${this.DOCTORS}/${doctor.id}`), doctor);

  }

  deleteDoctor(doctorId: number): Observable<any> {

    return this.http.delete<any>(this.url(`${this.DOCTORS}/${doctorId}`));

  }

  getAllDoctors(): Observable<Doctor[]> {

    return this.http.get<Doctor[]>(this.url(`${this.DOCTORS}`));

  }

  getDoctorById(doctorId: number): Observable<Doctor> {

    return this.http.get<Doctor>(this.url(`${this.DOCTORS}/${doctorId}`));

  }

  // ===================== CLINIC =====================

  addClinic(clinic: Clinic): Observable<Clinic> {

    return this.http.post<Clinic>(this.url(`${this.CLINICS}`), clinic);

  }

  updateClinic(clinic: Clinic): Observable<Clinic> {

    // Option A: PUT /clinics with body

    return this.http.put<Clinic>(this.url(`${this.CLINICS}`), clinic);

    // Option B (path-param style if clinic has id):

    // return this.http.put<Clinic>(this.url(`${this.CLINICS}/${clinic.id}`), clinic);

  }

  deleteClinic(clinicId: number): Observable<any> {

    return this.http.delete<any>(this.url(`${this.CLINICS}/${clinicId}`));

  }

  getAllClinics(): Observable<Clinic[]> {

    return this.http.get<Clinic[]>(this.url(`${this.CLINICS}`));

  }

  getClinicById(clinicId: number): Observable<Clinic> {

    return this.http.get<Clinic>(this.url(`${this.CLINICS}/${clinicId}`));

  }

  /** Adjust if your backend uses query: /clinics?location=... */

  getClinicsByLocation(location: string): Observable<Clinic[]> {

    return this.http.get<Clinic[]>(

      this.url(`${this.CLINICS}/location/${encodeURIComponent(location)}`)

    );

    // Query-param alternative:

    // return this.http.get<Clinic[]>(this.url(`${this.CLINICS}`), { params: { location } });

  }

  getClinicsByDoctorId(doctorId: number): Observable<Clinic[]> {

    return this.http.get<Clinic[]>(

      this.url(`${this.CLINICS}/doctor/${doctorId}`)

    );

  }

  // ===================== APPOINTMENT =====================

  createAppointment(appointment: Appointment): Observable<Appointment> {

    return this.http.post<Appointment>(this.url(`${this.APPOINTMENTS}`), appointment);

  }

  updateAppointment(appointment: Appointment): Observable<Appointment> {

    // Option A: PUT /appointments with body

    return this.http.put<Appointment>(this.url(`${this.APPOINTMENTS}`), appointment);

    // Option B (path-param style if appointment has id):

    // return this.http.put<Appointment>(this.url(`${this.APPOINTMENTS}/${appointment.id}`), appointment);

  }

  deleteAppointment(appointmentId: number): Observable<any> {

    return this.http.delete<any>(this.url(`${this.APPOINTMENTS}/${appointmentId}`));

  }

  getAllAppointments(): Observable<Appointment[]> {

    return this.http.get<Appointment[]>(this.url(`${this.APPOINTMENTS}`));

  }

  getAppointmentById(appointmentId: number): Observable<Appointment> {

    return this.http.get<Appointment>(this.url(`${this.APPOINTMENTS}/${appointmentId}`));

  }

  getAppointmentsByClinic(clinicId: number): Observable<Appointment[]> {

    return this.http.get<Appointment[]>(

      this.url(`${this.APPOINTMENTS}/clinic/${clinicId}`)

    );

  }

  getAppointmentsByPatient(patientId: number): Observable<Appointment[]> {

    return this.http.get<Appointment[]>(

      this.url(`${this.APPOINTMENTS}/patient/${patientId}`)

    );

  }

  /** Adjust if your backend uses query: /appointments?status=... */

  getAppointmentsByStatus(status: string): Observable<Appointment[]> {

    return this.http.get<Appointment[]>(

      this.url(`${this.APPOINTMENTS}/status/${encodeURIComponent(status)}`)

    );

    // Query-param alternative:

    // return this.http.get<Appointment[]>(this.url(`${this.APPOINTMENTS}`), { params: { status } });

  }

  // ===================== USER =====================

  getUserById(userId: number): Observable<User> {

    return this.http.get<User>(this.url(`${this.USERS}/${userId}`));

  }

}
