package com.edutech.progressive.service.impl;

import com.edutech.progressive.dto.DoctorDTO;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.entity.User;
import com.edutech.progressive.exception.DoctorAlreadyExistsException;
import com.edutech.progressive.repository.AppointmentRepository;
import com.edutech.progressive.repository.ClinicRepository;
import com.edutech.progressive.repository.DoctorRepository;
import com.edutech.progressive.repository.UserRepository;
import com.edutech.progressive.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class DoctorServiceImplJpa implements DoctorService {

    DoctorRepository doctorRepository;

    @Autowired
    ClinicRepository clinicRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public DoctorServiceImplJpa(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> getAllDoctors() throws Exception {
        return doctorRepository.findAll();
    }

    @Override
    public Integer addDoctor(Doctor doctor) throws Exception {
        Doctor existingDoctor = doctorRepository.findByEmail(doctor.getEmail());
        if (existingDoctor != null) {
            throw new DoctorAlreadyExistsException("Doctor with email " + doctor.getEmail() + " already exists");
        }
        return doctorRepository.save(doctor).getDoctorId();
    }

    @Override
    public List<Doctor> getDoctorSortedByExperience() throws Exception {
        List<Doctor> doctorList = doctorRepository.findAll();
        doctorList.sort(Comparator.comparing(Doctor::getYearsOfExperience));
        return doctorList;
    }

    @Override
    public void modifyDoctorDetails(DoctorDTO doctorDTO) throws Exception {

        // ✅ Check duplicate email
        Doctor existingByEmail = doctorRepository.findByEmail(doctorDTO.getEmail());
        if (existingByEmail != null && existingByEmail.getDoctorId() != doctorDTO.getDoctorId()) {
            throw new DoctorAlreadyExistsException("Doctor with email " + doctorDTO.getEmail() + " already exists");
        }

        // ✅ Update doctor entity using findById (primary key - always unique)
        Doctor doctorEntity = doctorRepository.findById(doctorDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorDTO.getDoctorId()));

        doctorEntity.setFullName(doctorDTO.getFullName());
        doctorEntity.setEmail(doctorDTO.getEmail());
        doctorEntity.setContactNumber(doctorDTO.getContactNumber());
        doctorEntity.setSpecialty(doctorDTO.getSpecialty());
        doctorEntity.setYearsOfExperience(doctorDTO.getYearsOfExperience());
        doctorRepository.save(doctorEntity);

        // ✅ FIXED: findByDoctorId now returns List — get first result
        List<User> users = userRepository.findByDoctorId(doctorDTO.getDoctorId());
        if (users != null && !users.isEmpty()) {
            User linkedUser = users.get(0);

            // Update username if provided and not taken by another user
            if (doctorDTO.getUsername() != null && !doctorDTO.getUsername().isEmpty()) {
                User existingUsername = userRepository.findByUsername(doctorDTO.getUsername());
                if (existingUsername != null
                        && existingUsername.getUserId() != linkedUser.getUserId()) {
                    throw new DoctorAlreadyExistsException("Username " + doctorDTO.getUsername() + " already taken");
                }
                linkedUser.setUsername(doctorDTO.getUsername());
            }

            // Update password if provided and different
            if (doctorDTO.getPassword() != null && !doctorDTO.getPassword().isEmpty()) {
                if (!passwordEncoder.matches(doctorDTO.getPassword(), linkedUser.getPassword())) {
                    linkedUser.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
                }
            }

            userRepository.save(linkedUser);
        }
    }

    @Override
    public void deleteDoctor(int doctorId) throws Exception {
        clinicRepository.deleteByDoctorId(doctorId);
        userRepository.deleteByDoctorId(doctorId);
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws Exception {
        // ✅ FIXED: use findById instead of findByDoctorId
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + doctorId));
    }

    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        Doctor oldDoctor = doctorRepository.findById(doctor.getDoctorId()).orElseThrow();
        oldDoctor.setFullName(doctor.getFullName());
        oldDoctor.setSpecialty(doctor.getSpecialty());
        oldDoctor.setContactNumber(doctor.getContactNumber());
        oldDoctor.setEmail(doctor.getEmail());
        oldDoctor.setYearsOfExperience(doctor.getYearsOfExperience());
        doctorRepository.save(oldDoctor);
    }
}