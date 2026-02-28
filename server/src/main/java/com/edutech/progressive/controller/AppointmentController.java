package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.service.AppointmentService;

import org.springframework.http.ResponseEntity;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // GET /appointment - Returns 200
    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointment() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    // POST /appointment - Returns 201 and new appointment ID
    @PostMapping
    public ResponseEntity<Integer> createAppointment(@RequestBody Appointment appointment) {
        int newId = appointmentService.createAppointment(appointment);
        return ResponseEntity.status(201).body(newId);
    }

    // PUT /appointment/{appointmentID} - Returns 200
    @PutMapping("/{appointmentID}")
    public ResponseEntity<Void> updateAppointment(@PathVariable int appointmentID,
                                                  @RequestBody Appointment appointment) {
        // Ensure the path ID is set on the object
        appointment.setAppointmentId(appointmentID);
        appointmentService.updateAppointment(appointment);
        return ResponseEntity.ok().build();
    }

    // GET /appointment/{appointmentID} - Returns 200
    @GetMapping("/{appointmentID}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable int appointmentID) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentID);
        return ResponseEntity.ok(appointment);
    }

    // GET /appointment/clinic/{clinicID} - Returns 200
    @GetMapping("/clinic/{clinicID}")
    public ResponseEntity<List<Appointment>> getAppointmentByClinic(@PathVariable int clinicID) {
        return ResponseEntity.ok(appointmentService.getAppointmentByClinic(clinicID));
    }

    // GET /appointment/patient/{patientID} - Returns 200
    @GetMapping("/patient/{patientID}")
    public ResponseEntity<List<Appointment>> getAppointmentByPatient(@PathVariable int patientID) {
        return ResponseEntity.ok(appointmentService.getAppointmentByPatient(patientID));
    }

    // GET /appointment/status/{status} - Returns 200
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentByStatus(@PathVariable String status) {
        return ResponseEntity.ok(appointmentService.getAppointmentByStatus(status));
    }
}
