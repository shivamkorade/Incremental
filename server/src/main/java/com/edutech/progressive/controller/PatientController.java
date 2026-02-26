package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;
import com.edutech.progressive.service.impl.PatientServiceImplArraylist;
import com.edutech.progressive.service.impl.PatientServiceImplJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/patient", produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientController {

    // Use @Autowired instead of constructor injection
    @Autowired
    private PatientServiceImplArraylist arrayListService;

    @Autowired
    private PatientServiceImplJpa jpaService;

    

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        try {
            List<Patient> patients = jpaService.getAllPatients();
            return ResponseEntity.ok(patients);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int patientId) {
        try {
            Patient patient = jpaService.getPatientById(patientId);
            if (patient == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(patient);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> addPatient(@RequestBody Patient patient) {
        try {
            Integer id = jpaService.addPatient(patient);
            if (id == null) id = 0;
            return ResponseEntity.created(URI.create("/patient/" + id)).body(id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{patientId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePatient(@PathVariable int patientId,
                                              @RequestBody Patient patient) {
        try {
            // Make path id authoritative if setter exists
            try {
                patient.getClass().getMethod("setPatientId", Integer.class).invoke(patient, patientId);
            } catch (Exception ignore) { /* ignore if not present */ }

            jpaService.updatePatient(patient);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable int patientId) {
        try {
            jpaService.deletePatient(patientId);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    @GetMapping("/fromArrayList")
    public ResponseEntity<List<Patient>> getAllPatientFromArrayList() {
        try {
            return ResponseEntity.ok(arrayListService.getAllPatients());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping(value = "/toArrayList", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addPatientToArrayList(@RequestBody Patient patient) {
        try {
            arrayListService.addPatient(patient);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/fromArrayList/sorted")
    public ResponseEntity<List<Patient>> getAllPatientSortedByNameFromArrayList() {
        try {
            return ResponseEntity.ok(arrayListService.getAllPatientSortedByName());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}