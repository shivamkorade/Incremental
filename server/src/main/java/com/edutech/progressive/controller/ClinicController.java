package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.service.impl.ClinicServiceImplJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinic")
public class ClinicController {

    @Autowired
    ClinicServiceImplJpa c;

    public ClinicController(ClinicServiceImplJpa c) {
        this.c = c;
    }

    @GetMapping
    public ResponseEntity<List<Clinic>> getAllClinics() {
        try {
            return new ResponseEntity<>(c.getAllClinics(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ FIXED: added @GetMapping and @PathVariable
    @GetMapping("/{clinicId}")
    public ResponseEntity<?> getClinicById(@PathVariable int clinicId) {
        try {
            return new ResponseEntity<>(c.getClinicById(clinicId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Clinic not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addClinic(@RequestBody Clinic clinic) {
        try {
            return new ResponseEntity<>(c.addClinic(clinic), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ FIXED: added @PutMapping and @PathVariable
    @PutMapping("/{clinicId}")
    public ResponseEntity<?> updateClinic(@PathVariable int clinicId, @RequestBody Clinic clinic) {
        try {
            clinic.setClinicId(clinicId);
            c.updateClinic(clinic);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ FIXED: added @DeleteMapping and @PathVariable
    @DeleteMapping("/{clinicId}")
    public ResponseEntity<?> deleteClinic(@PathVariable int clinicId) {
        try {
            c.deleteClinic(clinicId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/location/{location}")
    public ResponseEntity<List<Clinic>> getAllClinicByLocation(@PathVariable String location) {
        try {
            return new ResponseEntity<>(c.getAllClinicByLocation(location), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ✅ FIXED: added @GetMapping and @PathVariable
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<?> getAllClinicByDoctorId(@PathVariable int doctorId) {
        try {
            return new ResponseEntity<>(c.getAllClinicByDoctorId(doctorId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}