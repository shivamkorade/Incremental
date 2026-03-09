package com.edutech.progressive.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer> {
    // Derived Query - find bills by patient ID
    List<Billing> findByPatient_PatientId(Integer patientId);

    // JPQL Delete - must be transactional & modifying
    @Modifying
    @Transactional
    @Query("DELETE FROM Billing b WHERE b.patient.patientId = :patientId")
    void deleteByPatientId(@Param("patientId") Integer patientId);

}