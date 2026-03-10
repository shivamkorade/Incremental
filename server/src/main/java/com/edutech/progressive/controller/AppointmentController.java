

package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.web.server.ServerHttpSecurity.HttpsRedirectSpec;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> createAppointment(@RequestBody Appointment appointment) {
        return new ResponseEntity<>(appointmentService.createAppointment(appointment), HttpStatus.CREATED);
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Void> updateAppointment(@PathVariable int appointmentId, @RequestBody Appointment appointment) {
        appointment.setAppointmentId(appointmentId);
        appointmentService.updateAppointment(appointment); 
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int appointmentId) {
        return null;
    }

    public ResponseEntity<List<Appointment>> getAppointmentByClinic(@PathVariable int clinicId) {
        // return new ResponseEntity<>(as.getAppointmentByClinic(clinicId), HttpStatus.OK);
        return null;
    }

    public ResponseEntity<List<Appointment>> getAppointmentByPatient(@PathVariable int patientId) {
        // return new ResponseEntity<>(as.getAppointmentByPatient(patientId), HttpStatus.OK);
        return null;
    }

    @GetMapping("/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentByStatus(@PathVariable String status) {
        return new ResponseEntity<>(appointmentService.getAppointmentByStatus(status), HttpStatus.OK);
    }
}



