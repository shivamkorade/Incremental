package com.edutech.progressive.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Clinic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clinicId;
    private String clinicName;
    private String location;

    // Keep doctorId column for backward compatibility & existing queries
    private int doctorId;

    private String contactNumber;
    private int establishedYear;

    // --- Association: Many Clinics belong to One Doctor ---
    // We join on the same column "doctorId". Mark insertable/updatable false so the integer field remains the write source.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctorId", referencedColumnName = "doctorId", insertable = false, updatable = false)
    @JsonIgnore // avoid infinite recursion / large payloads in JSON
    private Doctor doctor;

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

    // Association getter/setter (read-only from JPA perspective due to insertable=false/updatable=false)
    public Doctor getDoctor() {
        return doctor;
    }
    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
        // Keep doctorId in sync if needed
        if (doctor != null) {
            this.doctorId = doctor.getDoctorId();
        }
    }

    @Override
    public String toString() {
        return "Clinic [clinicId=" + clinicId + ", clinicName=" + clinicName + ", location=" + location + ", doctorId="
                + doctorId + ", contactNumber=" + contactNumber + ", establishedYear=" + establishedYear + "]";
    }
}