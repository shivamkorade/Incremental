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
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findByStatus(String status);

    // ✅ because Appointment has `Clinic clinic` and Clinic has `clinicId`
    List<Appointment> findByClinic_ClinicId(int clinicId);

    // ✅ because Appointment has `Patient patient` and Patient has `patientId`
    List<Appointment> findByPatient_PatientId(int patientId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Appointment a WHERE a.patient.patientId = :patientId")
    void deleteByPatientId(@Param("patientId") int patientId);

    @Query(" SELECT DISTINCT a.patient FROM Appointment a WHERE a.clinic.doctorId = :doctorId ")
    List<com.edutech.progressive.entity.Patient> findPatientsByDoctorId(
            @Param("doctorId") int doctorId);
}