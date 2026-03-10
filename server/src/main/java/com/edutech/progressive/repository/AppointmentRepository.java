package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.progressive.entity.Appointment;
import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>{

    // public List<Appointment> findByClinicId(Integer clinicId);
    // public List<Appointment> findByPatientId(Integer patientId);
    public List<Appointment> findByStatus(String status);
    // void deleteByDoctorId(int doctorId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.patient.patientId = :patientId")
    void deleteByPatientId(@Param("patientId") int patientId);
}