package com.edutech.progressive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.repository.ClinicRepository;
import com.edutech.progressive.service.ClinicService;

@Service
public class ClinicServiceImplJpa implements ClinicService {


    @Autowired
    ClinicRepository cr;

    

    public ClinicServiceImplJpa(ClinicRepository cr) {
        this.cr = cr;
    }

    @Override
    public List<Clinic> getAllClinics() throws Exception {
     return cr.findAll();
    }

    @Override
    public Clinic getClinicById(int clinicId) throws Exception {
        try {
            return cr.findById(clinicId).orElseThrow();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Integer addClinic(Clinic clinic) throws Exception {
     return cr.save(clinic).getClinicId();
     
    }

    @Override
    public void updateClinic(Clinic clinic) throws Exception {
       Clinic d=cr.findById(clinic.getClinicId()).orElseThrow();
       d.setClinicName(clinic.getClinicName());
       d.setContactNumber(clinic.getContactNumber());
       d.setDoctorId(clinic.getDoctorId());
       d.setEstablishedYear(clinic.getEstablishedYear());
       d.setLocation(clinic.getLocation());
       cr.save(d);
    }

    @Override
    public void deleteClinic(int clinicId) throws Exception {
       cr.deleteById(clinicId);
    }

    @Override
    public List<Clinic> getAllClinicByLocation(String location) {
    return cr.findByLocation(location);
    }

    @Override
    public List<Clinic> getAllClinicByDoctorId(int doctorId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllClinicByDoctorId'");
    }

}
