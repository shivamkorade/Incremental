package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    // Optional<Patient> findByEmail(String email);

    // Derived query (as required): findByPatientId
    Patient findByPatientId(Integer patientId);

    // If your Patient has 'fullName' field:
    @Query("SELECT p FROM Patient p ORDER BY p.fullName ASC")
    List<Patient> getAllPatientSortedByName();

    // If your Patient has 'name' instead, use this line instead:
    // @Query("SELECT p FROM Patient p ORDER BY p.name ASC")
    // List<Patient> getAllPatientSortedByName();

    Optional<Patient> findByEmail(String email);
}