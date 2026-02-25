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
        // System.out.println("Welcome to MediConnect Progressive Project!");
        System.out.println("Hello MediConnect Progressive Project");

        // Patient patient = new Patient(0, "TanmayDhumne",Date.valueOf("2003-09-09"), "8830971721", "asdasda@gmail.com", "asdasdasd");
        // Patient patient2 = new Patient(0, "TanmayDhumne2",Date.valueOf("2003-09-09"), "8830971722", "as2dasda@gmail.com", "asdasdasd");
        // PatientDAOImpl impl = new PatientDAOImpl();
        // PatientServiceImplJdbc obj = new PatientServiceImplJdbc(impl);

      
            // obj.addPatient(patient);
            // System.out.println( obj.getPatientById(11));
            // System.out.println(obj.getAllPatients());
            // obj.deletePatient(17);

            // obj.updatePatient(patient);


            // Doctor doctor = new Doctor(0, "Dr.X", "xyz", "88282828893", "adefdaf@gmail.com", 3);
            //  Doctor doctor2 = new Doctor(1, "Dr.X2", "xy2z", "88282828893", "adefdaf@2gmail.com", 3);
            // DoctorDAOImpl impl2 = new DoctorDAOImpl();
            // DoctorServiceImplJdbc obj2 = new DoctorServiceImplJdbc(impl2);

            // obj2.addDoctor(doctor);
            // System.out.println(obj2.getDoctorById(1));
        //    System.err.println(obj2.getAllDoctors());
            // obj2.deleteDoctor(2);
            // obj2.updateDoctor(doctor2);
       

            Clinic clinic = new Clinic(0, "clinic", "xyz", 1, "88282828893", 2003);
            ClinicDAOImpl impl3 = new ClinicDAOImpl();
            ClinicServiceImplJdbc obj3 = new ClinicServiceImplJdbc(impl3);

            // obj3.addClinic(clinic);
        //    System.out.println( obj3.getClinicById(45));
            // System.out.println(obj3.getAllClinics());
            obj3.deleteClinic(44);
    }
}
