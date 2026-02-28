package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.edutech.progressive.dao.PatientDAOImpl;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;

public class PatientServiceImplJdbc implements PatientService {

    PatientDAOImpl impl = new PatientDAOImpl();
    
    public PatientServiceImplJdbc(PatientDAOImpl impl) {
        this.impl = impl;
    }

    @Override
    public List<Patient> getAllPatients() {
        try {
            return impl.getAllPatients();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer addPatient(Patient patient)  {
        try {
            return impl.addPatient(patient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<Patient> getAllPatientSortedByName() {
        List<Patient> patient;
        try {
            patient = impl.getAllPatients();
            Collections.sort(patient);
            return patient;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updatePatient(Patient patient){
          try {
            impl.updatePatient(patient);
          } catch (SQLException e) {
            e.printStackTrace();
          }
    }

    public void deletePatient(int patientId){
           try {
            impl.deletePatient(patientId);
           } catch (SQLException e) {
            e.printStackTrace();
           }
    }

    public Patient getPatientById(int patientId){
        try {
            return impl.getPatientById(patientId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Patient getPatientByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPatientByEmail'");
    }
    
}