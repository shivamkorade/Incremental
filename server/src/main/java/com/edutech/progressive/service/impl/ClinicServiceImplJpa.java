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
        return cr.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("Clinic not found with id: " + clinicId));
    }

    @Override
    public Integer addClinic(Clinic clinic) throws Exception {
        return cr.save(clinic).getClinicId();
    }

    @Override
    public void updateClinic(Clinic clinic) throws Exception {
        Clinic existing = cr.findById(clinic.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found with id: " + clinic.getClinicId()));
        existing.setClinicName(clinic.getClinicName());
        existing.setContactNumber(clinic.getContactNumber());
        existing.setDoctorId(clinic.getDoctorId());
        existing.setEstablishedYear(clinic.getEstablishedYear());
        existing.setLocation(clinic.getLocation());
        cr.save(existing);
    }

    @Override
    public void deleteClinic(int clinicId) throws Exception {
        if (!cr.existsById(clinicId)) {
            throw new RuntimeException("Clinic not found with id: " + clinicId);
        }
        cr.deleteById(clinicId);
    }

    @Override
    public List<Clinic> getAllClinicByLocation(String location) {
        return cr.findByLocation(location);
    }

    // ✅ FIXED: implemented instead of throwing UnsupportedOperationException
    @Override
    public List<Clinic> getAllClinicByDoctorId(int doctorId) {
        return cr.findAllByDoctorId(doctorId);
    }
}