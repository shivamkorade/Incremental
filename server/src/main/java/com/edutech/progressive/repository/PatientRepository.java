package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Patient;
import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer>{
    Patient findByEmail(String email);
    List<Patient> findByPatientId(int patientId);
    
}