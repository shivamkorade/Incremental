package com.edutech.progressive.service.impl;

import java.util.List;

import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;

public class PatientServiceImplJdbc implements PatientService {

    @Override
    public List<Patient> getAllPatients() {
        return null;
    }

    @Override
    public Integer addPatient(Patient patient) {
        return -1;
    }

    @Override
    public List<Patient> getAllPatientSortedByName() {
        return null;
    }

    public void updatePatient(Patient patient){

    }

    public void deletePatient(int patientId){

    }

    public Patient getPatientById(int patientId){
        return null;
    }
    
}