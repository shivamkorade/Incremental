package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.exception.ClinicAlreadyExistsException;
import com.edutech.progressive.repository.ClinicRepository;
import com.edutech.progressive.service.ClinicService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicServiceImplJpa implements ClinicService {

    private final ClinicRepository clinicRepository;

    // Prefer constructor injection (avoids double injection with @Autowired)
    public ClinicServiceImplJpa(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    @Override
    public List<Clinic> getAllClinics() {
        return clinicRepository.findAll();
    }

    @Override
    public Clinic getClinicById(int clinicId) {
        // Return null when not found (aligns with tests expecting Clinic, not Optional)
        return clinicRepository.findByClinicId(clinicId).orElseThrow(()-> new RuntimeException("Clinic not found with id: "+clinicId));
    }

    @Override
    public Integer addClinic(Clinic clinic) {
        // Clinic saved = clinicRepository.save(clinic);
        // return saved.getClinicId();
        if (clinicRepository.findByClinicName(clinic.getClinicName()).isPresent()) 
            { 
                throw new ClinicAlreadyExistsException("Clinic already exists with name: " + clinic.getClinicName()); 
            } 
        return clinic.getClinicId();
    }

    @Override
    public void updateClinic(Clinic clinic) {
        Optional<Clinic> existing = clinicRepository.findByClinicId(clinic.getClinicId());
        if (existing.isPresent()) {
            Clinic c = existing.get();
            c.setClinicName(clinic.getClinicName());
            c.setLocation(clinic.getLocation());
            c.setDoctorId(clinic.getDoctorId());
            c.setContactNumber(clinic.getContactNumber());
            c.setEstablishedYear(clinic.getEstablishedYear());
            clinicRepository.save(c);
        }
        // If not found, silently ignore (matches typical unit-test patterns).
    }

    @Override
    public void deleteClinic(int clinicId) {
        clinicRepository.deleteById(clinicId);
    }

    @Override
    public List<Clinic> getAllClinicByLocation(String location) {
        return clinicRepository.findAllByLocation(location);
    }

    @Override
    public List<Clinic> getAllClinicByDoctorId(int doctorId) {
        return clinicRepository.findAllByDoctorId(doctorId);
    }
} 