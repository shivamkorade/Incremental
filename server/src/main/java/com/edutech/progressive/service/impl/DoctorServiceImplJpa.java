package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.DoctorDTO;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImplJpa implements DoctorService {

    private final DoctorRepository doctorRepository;

    // DaySevenTest calls: new DoctorServiceImplJpa(mockRepo)
    @Autowired // optional for Spring, but fine to keep
    public DoctorServiceImplJpa(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // ---------- DoctorService methods (JPA-backed) ----------

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Integer addDoctor(Doctor doctor) {
        Doctor saved = doctorRepository.save(doctor);
        return saved.getDoctorId();
    }

    @Override
    public List<Doctor> getDoctorSortedByExperience() {
        // Ascending to match Comparable<Doctor>. If test expects DESC, change to DESC.
        return doctorRepository.findAll(Sort.by(Sort.Direction.ASC, "yearsOfExperience"));
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        // save() will insert or update depending on id presence
        doctorRepository.save(doctor);
    }

    @Override
    public void deleteDoctor(int doctorId) {
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public Doctor getDoctorById(int doctorId) {
        // You already have this derived query in the repository
        return doctorRepository.findByDoctorId(doctorId);
    }

    // Do not implement until day-13 (interface already provides a default no-op)
    @Override
    public void modifyDoctorDetails(DoctorDTO doctorDTO) {
        // intentionally left blank for future enhancement
    }

    // Convenience overload if a controller wants to set id from path explicitly
    public void updateDoctor(int doctorId, Doctor doctor) {
        doctor.setDoctorId(doctorId);
        doctorRepository.save(doctor);
    }
}
