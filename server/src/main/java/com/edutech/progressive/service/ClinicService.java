package com.edutech.progressive.service;

import com.edutech.progressive.entity.Clinic;
import java.util.List;

public interface ClinicService {

    List<Clinic> getAllClinics();

    // Return entity directly (nullable) to match test expectations
    Clinic getClinicById(int clinicId);

    Integer addClinic(Clinic clinic);

    void updateClinic(Clinic clinic);

    void deleteClinic(int clinicId);

    // Do not implement these methods in ClinicServiceImplJdbc.java class
    default List<Clinic> getAllClinicByLocation(String location) { return null; }

    default List<Clinic> getAllClinicByDoctorId(int doctorId) { return null; }
} 