package com.edutech.progressive.dao;

import com.edutech.progressive.entity.Patient;

import java.sql.SQLException;
import java.util.List;

public interface PatientDAO{
    Integer addPatient(Patient patient) throws SQLException;
    Patient getPatientById(Integer patientId)throws SQLException;
    void updatePatient (Patient patient)throws SQLException;
    void deletePatient (Integer patientId)throws SQLException;
    List<Patient> getAllPatients()throws SQLException;
}