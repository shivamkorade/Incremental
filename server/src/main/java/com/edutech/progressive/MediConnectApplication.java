package com.edutech.progressive;

import java.sql.Date;
import java.sql.SQLException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.edutech.progressive.dao.ClinicDAOImpl;
import com.edutech.progressive.dao.DoctorDAOImpl;
import com.edutech.progressive.dao.PatientDAOImpl;
import com.edutech.progressive.entity.Clinic;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.impl.ClinicServiceImplJdbc;
import com.edutech.progressive.service.impl.ClinicServiceImplJpa;
import com.edutech.progressive.service.impl.DoctorServiceImplJdbc;
import com.edutech.progressive.service.impl.PatientServiceImplJdbc;

@SpringBootApplication
public class MediConnectApplication {
    public static void main(String[] args) {
        System.out.println("Hello MediConnect Progressive Project");
        // System.out.println("Hello MediConnect Progressive Project");

        
    }
}
