// package com.wecp.progressive.service.impl;

// import com.wecp.progressive.entity.Patient;
// import com.wecp.progressive.service.PatientService;

// import java.util.ArrayList;
// import java.util.Collection;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.List;

// import org.springframework.stereotype.Service;

// @Service
// public class PatientServiceImplArraylist implements PatientService {

//     private static List<Patient> patientList = new ArrayList<>();

//     @Override
//     public List<Patient> getAllPatients() {
//         return patientList;
//     }

//     @Override
//     public Integer addPatient(Patient patient) {
//         patientList.add(patient);
//         return patientList.size();
//     }

//     @Override
//     public List<Patient> getAllPatientSortedByName() {
//         List<Patient> sortedPatients = patientList;
//         // sortedPatients.sort(Comparator.comparing(Patient::getFullName));
//         Collections.sort(sortedPatients);
//         return sortedPatients;
//     }

//     @Override
//     public void emptyArrayList() {
//         patientList = new ArrayList<>();
//     }
// }



package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.PatientService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PatientServiceImplArraylist implements PatientService {

    private static List<Patient> patientList = new ArrayList<>();

    @Override
    public List<Patient> getAllPatients() {
        return patientList;
    }

    @Override
    public Integer addPatient(Patient patient) {
        patientList.add(patient);
        return patientList.size();
    }

    @Override
    public List<Patient> getAllPatientSortedByName() {
        List<Patient> sortedPatients = new ArrayList<>(patientList);
        // sortedPatients.sort(Comparator.comparing(Patient::getFullName));
        Collections.sort(sortedPatients);
        return sortedPatients;
    }

    @Override
    public void emptyArrayList() {
        patientList = new ArrayList<>();
    }

    @Override
    public void updatePatient(Patient patient) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePatient'");
    }
    @Override
    public Patient getPatientById(int patientId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPatientById'");
    }

    @Override
    public void deletePatient(int patientId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePatient'");
    }
}