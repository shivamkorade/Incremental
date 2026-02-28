package com.edutech.progressive.service.impl;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.repository.AppointmentRepository;
import com.edutech.progressive.service.AppointmentService;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public int createAppointment(Appointment appointment) {
        Appointment saved = appointmentRepository.save(appointment);
        return saved.getAppointmentId(); // return generated ID
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        Appointment existing = appointmentRepository.findById(appointment.getAppointmentId())
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        existing.setAppointmentDate(appointment.getAppointmentDate());
        existing.setStatus(appointment.getStatus());
        existing.setPurpose(appointment.getPurpose());
        existing.setClinic(appointment.getClinic());
        existing.setPatient(appointment.getPatient());
        appointmentRepository.save(existing);
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
    }

    @Override
    public List<Appointment> getAppointmentByClinic(int clinicId) {
        return appointmentRepository.findByClinic_ClinicId(clinicId);
    }

    @Override
    public List<Appointment> getAppointmentByPatient(int patientId) {
        return appointmentRepository.findByPatient_PatientId(patientId);
    }

    @Override
    public List<Appointment> getAppointmentByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }
}
