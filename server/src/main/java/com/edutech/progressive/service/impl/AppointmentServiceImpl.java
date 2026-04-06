package com.edutech.progressive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.repository.AppointmentRepository;
import com.edutech.progressive.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    @Override
    public int createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment).getAppointmentId();
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        Appointment old = appointmentRepository.findById(appointment.getAppointmentId()).orElseThrow();
        old.setAppointmentDate(appointment.getAppointmentDate());
        old.setClinic(appointment.getClinic());
        old.setPatient(appointment.getPatient());
        old.setPurpose(appointment.getPurpose());
        old.setStatus(appointment.getStatus());
        if (appointment.getDoctorNotes() != null) {
            old.setDoctorNotes(appointment.getDoctorNotes());
        }
        appointmentRepository.save(old);
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow();
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

    @Override
    public List<com.edutech.progressive.entity.Patient> getPatientsByDoctorId(int doctorId) {
        return appointmentRepository.findPatientsByDoctorId(doctorId);
    }
}
