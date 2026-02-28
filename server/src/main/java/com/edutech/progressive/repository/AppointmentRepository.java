package com.edutech.progressive.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.progressive.entity.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    // Derived methods
    List<Appointment> findByClinic_ClinicId(Integer clinicId);
    List<Appointment> findByPatient_PatientId(Integer patientId);
    List<Appointment> findByStatus(String status);

    // JPQL delete methods (only patient and clinic, since doctor_id not in schema)
    @Transactional
    @Modifying
    @Query("DELETE FROM Appointment a WHERE a.patient.patientId = :patientId")
    void deleteByPatientId(Integer patientId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Appointment a WHERE a.clinic.clinicId = :clinicId")
    void deleteByClinicId(Integer clinicId);
}


