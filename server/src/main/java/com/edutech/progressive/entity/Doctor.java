package com.edutech.progressive.entity;

import java.util.Comparator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table
public class Doctor implements Comparable<Doctor> {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private int doctorId;
    private String fullName;
    private String specialty;
    private String contactNumber;
    private String email;
    private int yearsOfExperience;
    @ManyToOne
    @JoinColumn(name = "clinicId")
    private Clinic clinic;

    public Doctor() {
    }

    public Doctor(int doctorId, String fullName, String specialty, String contactNumber, String email, int yearsOfExperience) {
        this.doctorId = doctorId;
        this.fullName = fullName;
        this.specialty = specialty;
        this.contactNumber = contactNumber;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
    }
    
    
    public Doctor(String fullName, String specialty, String contactNumber, String email, int yearsOfExperience,
            Clinic clinic) {
        this.fullName = fullName;
        this.specialty = specialty;
        this.contactNumber = contactNumber;
        this.email = email;
        this.yearsOfExperience = yearsOfExperience;
        this.clinic = clinic;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    @Override
    public int compareTo(Doctor otherDoctor) {
        return Integer.compare(yearsOfExperience, otherDoctor.getYearsOfExperience());
    }
}
