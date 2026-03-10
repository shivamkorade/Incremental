package com.edutech.progressive.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Billing;
import com.edutech.progressive.entity.Patient;

import java.util.List;


@Repository
public interface BillingRepository extends JpaRepository<Billing, Integer>{
    List<Billing> findByPatient_PatientId(int patientId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Billing b WHERE b.patient.patientId = :patientId")
    void deleteByPatientId(@Param("patientId") int patientId);

    // @Modifying
    // @Transactional
    // @Query("delete from billing b where b.patient.patientId = :patientid")
    // void deleteByPatientId(@Param("patientId") int patientid);
}