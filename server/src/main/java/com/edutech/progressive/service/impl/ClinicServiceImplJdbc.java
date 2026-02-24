package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.edutech.progressive.dao.ClinicDAOImpl;
import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.service.ClinicService;

public class ClinicServiceImplJdbc implements ClinicService {

    ClinicDAOImpl impl = new ClinicDAOImpl();
    


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
        return null;
    }

    @Override
    public Clinic getClinicById(int clinicId) {
        try {
            return impl.getClinicById(clinicId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
        
    }

    @Override
    public Integer addClinic(Clinic clinic) {
        try {
            return impl.addClinic(clinic);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void updateClinic(Clinic clinic) {
        try {
            impl.updateClinic(clinic);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void deleteClinic(int clinicId) {
        try {
            impl.deleteClinic(clinicId);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

}