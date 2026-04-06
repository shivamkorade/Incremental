package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Appointment;
import com.edutech.progressive.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return new ResponseEntity<>(appointmentService.getAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<?> getAppointmentById(@PathVariable int appointmentId) {
        try {
            return new ResponseEntity<>(appointmentService.getAppointmentById(appointmentId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Appointment not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/clinic/{clinicId}")
    public ResponseEntity<List<Appointment>> getAppointmentByClinic(@PathVariable int clinicId) {
        return new ResponseEntity<>(appointmentService.getAppointmentByClinic(clinicId), HttpStatus.OK);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getAppointmentByPatient(@PathVariable int patientId) {
        return new ResponseEntity<>(appointmentService.getAppointmentByPatient(patientId), HttpStatus.OK);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Appointment>> getAppointmentByStatus(@PathVariable String status) {
        return new ResponseEntity<>(appointmentService.getAppointmentByStatus(status), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            int id = appointmentService.createAppointment(appointment);
            return new ResponseEntity<>(Map.of("message", "Appointment created successfully", "appointmentId", id),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Failed to create appointment: " + e.getMessage()),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<Void> updateAppointment(@PathVariable int appointmentId,
            @RequestBody Appointment appointment) {
        appointment.setAppointmentId(appointmentId);
        appointmentService.updateAppointment(appointment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable int appointmentId) {
        try {
            Appointment appt = appointmentService.getAppointmentById(appointmentId);
            appt.setStatus("Canceled");
            appointmentService.updateAppointment(appt);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{appointmentId}/approve")
    public ResponseEntity<?> approveAppointment(@PathVariable int appointmentId) {
        try {
            Appointment appt = appointmentService.getAppointmentById(appointmentId);
            appt.setStatus("Approved");
            appointmentService.updateAppointment(appt);
            return new ResponseEntity<>(Map.of("message", "Appointment approved"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{appointmentId}/reject")
    public ResponseEntity<?> rejectAppointment(@PathVariable int appointmentId) {
        try {
            Appointment appt = appointmentService.getAppointmentById(appointmentId);
            appt.setStatus("Rejected");
            appointmentService.updateAppointment(appt);
            return new ResponseEntity<>(Map.of("message", "Appointment rejected"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{appointmentId}/notes")
    public ResponseEntity<?> addDoctorNotes(@PathVariable int appointmentId, @RequestBody Map<String, String> body) {
        try {
            Appointment appt = appointmentService.getAppointmentById(appointmentId);
            appt.setDoctorNotes(body.get("doctorNotes"));
            appointmentService.updateAppointment(appt);
            return new ResponseEntity<>(Map.of("message", "Notes saved"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("message", "Not found"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/patients/doctor/{doctorId}")
    public ResponseEntity<?> getPatientsByDoctor(@PathVariable int doctorId) {
        return new ResponseEntity<>(appointmentService.getPatientsByDoctorId(doctorId), HttpStatus.OK);
    }
}