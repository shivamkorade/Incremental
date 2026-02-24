package com.edutech.progressive.service.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.edutech.progressive.dao.DoctorDAOImpl;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.service.DoctorService;

public class DoctorServiceImplJdbc implements DoctorService {

    DoctorDAOImpl impl =new DoctorDAOImpl();
    

    public DoctorServiceImplJdbc(DoctorDAOImpl impl) {
        this.impl = impl;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        try {
            return impl.getAllDoctors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer addDoctor(Doctor doctor) {
        try {
            return impl.addDoctor(doctor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    @Override
    public List<Doctor> getDoctorSortedByExperience() {
        List<Doctor> doctor;
        try {
            doctor = impl.getAllDoctors();
             Collections.sort(doctor);
        return doctor;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
       
    }

    public Doctor getDoctorById(int doctorId){
        try {
            return impl.getDoctorById(doctorId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void updateDoctor(Doctor doctor){
        try {
            impl.updateDoctor(doctor);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
    public void deleteDoctor(int doctorId){
        try {
            impl.deleteDoctor(doctorId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}