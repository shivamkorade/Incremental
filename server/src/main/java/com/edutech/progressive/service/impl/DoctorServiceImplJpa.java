package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.service.DoctorService; // if you have this; if not, keep as a simple @Service class
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * As per task: keep default/safe returns for now (future enhancements).
 * If there is a DoctorService interface, implement it; otherwise keep as @Service with no-op methods.
 */
@Service
public class DoctorServiceImplJpa  /* implements DoctorService */ {

    // Example safe defaults if your interface exists. If not, you can delete these methods.
    public List<Doctor> getAllDoctors() {
        return Collections.emptyList();
    }

    public Doctor getDoctorById(int doctorId) {
        return null;
    }

    public Integer addDoctor(Doctor doctor) {
        return 0;
    }

    public void updateDoctor(Doctor doctor) {
        // no-op
    }

    public void deleteDoctor(int doctorId) {
        // no-op
    }
}