package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.PatientDTO;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.exception.PatientAlreadyExistsException;
import com.edutech.progressive.exception.PatientNotFoundException;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImplJpa implements PatientService {

    private final PatientRepository patientRepository;

    // >>> This constructor is REQUIRED by your DaySix tests <<<
    @Autowired // Optional for Spring (not needed for the test, but fine to keep)
    public PatientServiceImplJpa(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Integer addPatient(Patient patient) {
        // Patient saved = patientRepository.save(patient);
        // // Adjust getter name if your entity uses a different id accessor
        // Integer id = saved.getPatientId();
        // return id != null ? id : 0;
        if (patientRepository.findByEmail(patient.getEmail()).isPresent()) {
            throw new PatientAlreadyExistsException("Patient already exists with email: " + patient.getEmail());
        }
        patientRepository.save(patient);
        return patient.getPatientId();
    }

    @Override
    public List<Patient> getAllPatientSortedByName() {
        // Uses your custom query in PatientRepository
        return patientRepository.getAllPatientSortedByName();
    }

    @Override
    public void updatePatient(Patient patient) {
        // save() works as update when id is present
        patientRepository.save(patient);
    }

    @Override
    public void deletePatient(int patientId) {
        // If your PK is patientId, this is correct
        patientRepository.deleteById(patientId);
    }

    @Override
    public Patient getPatientById(int patientId) {
        // Derived query method you already defined
        // return patientRepository.findByPatientId(patientId);
        return patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patientId));
    }

    @Override
    public void modifyPatientDetails(PatientDTO patientDTO) {
        // Deferred to day-13
    }

    @Override
    public Patient getPatientByEmail(String email) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getPatientByEmail'");
        Optional<Patient> optionalPatient = patientRepository.findByEmail(email);
        if (optionalPatient.isPresent()) {
            return optionalPatient.get();
        } else {
            throw new PatientNotFoundException("Patient not found with email: " + email);
        }
    }
}