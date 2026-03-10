package com.edutech.progressive.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Clinic;


@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Integer>{

    void deleteByDoctorId(int doctorId);
    List<Clinic> findByLocation(String location);

    // @Query("select c from Clinic c JOIN c.doctors d where d.doctorId = :doctorId")
    // Clinic findClinicByDoctorId(@Param("doctorId") int doctorId);
    
    // @Transactional
    // @Modifying
    // @Query("delete from Clinic c where c.id in (select d.clinic.id from Doctor d where d.doctorId = :doctorId)")
    // void deleteClinicByDoctorId(@Param("doctorId") int doctorId);
}