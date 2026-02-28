package com.edutech.progressive.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    Optional<Clinic> findByClinicId(int clinicId);

    List<Clinic> findAllByLocation(String location);

    @Query("SELECT c FROM Clinic c WHERE c.doctorId = :doctorId")
    List<Clinic> findAllByDoctorId(@Param("doctorId") int doctorId);

    Optional<Clinic> findByClinicName(String clinicName);

    @Transactional
    @Modifying
    @Query("DELETE FROM Clinic c WHERE c.doctorId = :doctorId")
    void deleteByDoctorId(@Param("doctorId") int doctorId);
}
