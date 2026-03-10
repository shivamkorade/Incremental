package com.edutech.progressive.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Clinic {
    @Id
    @GeneratedValue ( strategy = GenerationType.IDENTITY)
    private int clinicId;
    private String clinicName;
    private String location;
    private int doctorId;
    private String contactNumber;
    private int establishedYear;
    @OneToMany(mappedBy = "clinic" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Doctor> doctors = new ArrayList<>();

    public Clinic() {
    }

    public Clinic(int clinicId, String clinicName, String location, int doctorId, String contactNumber,
            int establishedYear) {
        this.clinicId = clinicId;
        this.clinicName = clinicName;
        this.location = location;
        this.doctorId = doctorId;
        this.contactNumber = contactNumber;
        this.establishedYear = establishedYear;
    }
    

    public Clinic(String clinicName, String location, int doctorId, String contactNumber, int establishedYear,
            List<Doctor> doctors) {
        this.clinicName = clinicName;
        this.location = location;
        this.doctorId = doctorId;
        this.contactNumber = contactNumber;
        this.establishedYear = establishedYear;
        this.doctors = doctors;
    }



    public int getClinicId() {
        return clinicId;
    }

    public void setClinicId(int clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getEstablishedYear() {
        return establishedYear;
    }

    public void setEstablishedYear(int establishedYear) {
        this.establishedYear = establishedYear;
    }

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }


}
