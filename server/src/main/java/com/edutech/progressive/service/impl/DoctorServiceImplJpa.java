


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
        Doctor existingDoctor = doctorRepository.findByEmail(doctorDTO.getEmail());
        User doctorUser = userRepository.findByDoctorId(doctorDTO.getDoctorId());
        if (existingDoctor != null && existingDoctor.getDoctorId() != doctorDTO.getDoctorId()) {
            throw new DoctorAlreadyExistsException("Doctor with email " + doctorDTO.getEmail() + " already exists");
            // throw new DoctorAlreadyExistsException
        }
        User user = userRepository.findByUsername(doctorDTO.getUsername());
        if (user != null && user.getDoctor().getDoctorId() != doctorDTO.getDoctorId()) {
            throw new DoctorAlreadyExistsException("User with username " + doctorDTO.getEmail() + " already exists");
        }
        else {
            doctorUser.setUsername(doctorDTO.getUsername());
        }
        if (!doctorUser.getPassword().equals(doctorDTO.getPassword())) {
            doctorUser.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));
        }
        userRepository.save(doctorUser);
        Doctor doctorEntity = new Doctor();
        doctorEntity.setDoctorId(doctorDTO.getDoctorId());
        doctorEntity.setFullName(doctorDTO.getFullName());
        doctorEntity.setEmail(doctorDTO.getEmail());
        doctorEntity.setContactNumber(doctorDTO.getContactNumber());
        doctorEntity.setSpecialty(doctorDTO.getSpecialty());
        doctorEntity.setYearsOfExperience(doctorDTO.getYearsOfExperience());
        doctorRepository.save(doctorEntity);
    }

    @Override
    public void deleteDoctor(int doctorId) throws Exception {
        // appointmentRepository.deleteByDoctorId(doctorId);
        clinicRepository.deleteByDoctorId(doctorId);
        userRepository.deleteByDoctorId(doctorId);
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public Doctor getDoctorById(int doctorId) throws Exception {
        return doctorRepository.findByDoctorId(doctorId);
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