package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.service.ClinicService;
import com.edutech.progressive.service.impl.ClinicServiceImplJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Clinic>> getAllClinics() throws Exception {
        return new ResponseEntity<>(c.getAllClinics(),HttpStatus.OK);
    }

    public ResponseEntity<Clinic> getClinicById(int clinicId) throws Exception {
        return new ResponseEntity<>(c.getClinicById(clinicId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> addClinic(@RequestBody Clinic clinic) throws Exception {
        return new ResponseEntity<>(c.addClinic(clinic),HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateClinic(int clinicId, Clinic clinic) throws Exception {
        c.updateClinic(clinic);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteClinic(int clinicId) throws Exception {
        c.deleteClinic(clinicId);
       return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/location/{location}")
    public ResponseEntity<List<Clinic>> getAllClinicByLocation(@PathVariable String location) {
        return new ResponseEntity<>(c.getAllClinicByLocation(location),HttpStatus.OK);
    }

    public ResponseEntity<List<Clinic>> getAllClinicByDoctorId(@PathVariable int doctorId) {
        return new ResponseEntity<>(c.getAllClinicByDoctorId(doctorId),HttpStatus.OK);
    }
}
