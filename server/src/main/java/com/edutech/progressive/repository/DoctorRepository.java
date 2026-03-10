package com.edutech.progressive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edutech.progressive.entity.Doctor;
import java.util.List;
import java.util.Optional;


@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>{
    Doctor findByEmail(String email);
    Doctor findByDoctorId(int doctorId);
    // List<Doctor> findAllByOrderByExperienceDesc();
}