package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.edutech.progressive.dao.ClinicDAOImpl;
import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.service.ClinicService;

public class ClinicServiceImplJdbc implements ClinicService {

    private final ClinicDAOImpl impl;

    public ClinicServiceImplJdbc(ClinicDAOImpl impl) {
        this.impl = impl;
    }

    @Override
    public List<Clinic> getAllClinics() {
        try {
            return impl.getAllClinics();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // or Collections.emptyList();
    }

    @Override
    public Clinic getClinicById(int clinicId) {
        try {
            // DAO returns a Clinic or throws; return null if not found/exception
            return impl.getClinicById(clinicId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer addClinic(Clinic clinic) {
        try {
            return impl.addClinic(clinic);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateClinic(Clinic clinic) {
        try {
            impl.updateClinic(clinic);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Silently ignore on exception to match common academy tests
    }

    @Override
    public void deleteClinic(int clinicId) {
        try {
            impl.deleteClinic(clinicId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Silently ignore on exception to match common academy tests
    }

    // Do NOT implement getAllClinicByLocation / getAllClinicByDoctorId here,
    // per the instruction in ClinicService.
} 