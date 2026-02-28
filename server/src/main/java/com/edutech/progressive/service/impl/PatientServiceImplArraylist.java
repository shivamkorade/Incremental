package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PatientServiceImplArraylist implements PatientService {

    private static final List<Patient> patientList = new ArrayList<>();

    @Override
    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientList);
    }

    @Override
    public Integer addPatient(Patient patient) {
        patientList.add(patient);
        return patientList.size();
    }

    @Override
    public List<Patient> getAllPatientSortedByName() {
        List<Patient> copy = new ArrayList<>(patientList);
        Collections.sort(copy); // assumes Patient implements Comparable by name
        return copy;
    }

    @Override
    public void emptyArrayList() {
        patientList.clear();
    }

    @Override
    public Patient getPatientByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPatientByEmail'");
    }
}