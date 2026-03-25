package com.edutech.progressive.repository;

import com.edutech.progressive.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    Clinic findByClinicName(String clinicName);

    List<Clinic> findByLocation(String location);

    // ✅ JPQL query to find all clinics by doctor ID
    @Query("SELECT c FROM Clinic c WHERE c.doctorId = :doctorId")
    List<Clinic> findAllByDoctorId(@Param("doctorId") int doctorId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Clinic c WHERE c.doctorId = :doctorId")
    void deleteByDoctorId(@Param("doctorId") int doctorId);
}