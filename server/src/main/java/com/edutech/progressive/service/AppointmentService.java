package com.edutech.progressive.service;

import java.util.List;
import com.edutech.progressive.entity.Appointment;

public interface AppointmentService {
    List<Appointment> getAllAppointments();
    int createAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);
    Appointment getAppointmentById(int appointmentId);
    List<Appointment> getAppointmentByClinic(int clinicId);
    List<Appointment> getAppointmentByPatient(int patientId);
    List<Appointment> getAppointmentByStatus(String status);
    List<com.edutech.progressive.entity.Patient> getPatientsByDoctorId(int doctorId);
}