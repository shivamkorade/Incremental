package com.edutech.progressive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.repository.AppointmentRepository;
import com.edutech.progressive.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService{


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
        Appointment oldAppointment = appointmentRepository.findById(appointment.getAppointmentId()).orElseThrow();
        oldAppointment.setAppointmentDate(appointment.getAppointmentDate());
        oldAppointment.setClinic(appointment.getClinic());
        oldAppointment.setPatient(appointment.getPatient());
        oldAppointment.setPurpose(appointment.getPurpose());
        oldAppointment.setStatus(appointment.getStatus());
        appointmentRepository.save(oldAppointment);
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow();
    }

    @Override
    public List<Appointment> getAppointmentByClinic(int clinicId) {
        // return ar.findByClinicId(clinicId);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointmentByClinic'");
    }

    @Override
    public List<Appointment> getAppointmentByPatient(int patientId) {
        // return ar.findByPatientId(patientId);
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAppointmentByPatient'");
    }

    @Override
    public List<Appointment> getAppointmentByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

}



