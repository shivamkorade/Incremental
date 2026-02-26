package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.PatientDTO;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PatientServiceImplJpa implements PatientService {

    @Override
    public List<Patient> getAllPatients() {
        return Collections.emptyList();
    }

    @Override
    public Integer addPatient(Patient patient) {
        return 0;
    }

    @Override
    public List<Patient> getAllPatientSortedByName() {
        return Collections.emptyList();
    }

    @Override
    public void updatePatient(Patient patient) {
        // no-op for now
    }

    @Override
    public void deletePatient(int patientId) {
        // no-op for now
    }

    @Override
    public Patient getPatientById(int patientId) {
        return null;
    }

    @Override
    public void modifyPatientDetails(PatientDTO patientDTO) {
        // deferred to day-13
    }
}