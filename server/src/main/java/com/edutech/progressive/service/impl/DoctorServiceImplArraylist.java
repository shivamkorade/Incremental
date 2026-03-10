// package com.wecp.progressive.service.impl;

// import com.wecp.progressive.entity.Doctor;
// import com.wecp.progressive.service.DoctorService;

// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Comparator;
// import java.util.List;

// public class DoctorServiceImplArraylist  implements DoctorService {

//     private static List<Doctor> doctorList = new ArrayList<>();

//     @Override
//     public void emptyArrayList() {
//         doctorList = new ArrayList<>();
//     }

//     @Override
//     public List<Doctor> getAllDoctors() {
//         return doctorList;
//     }

//     @Override
//     public Integer addDoctor(Doctor doctor) {
//         doctorList.add(doctor);
//         return doctorList.size();
//     }

//     @Override
//     public List<Doctor> getDoctorSortedByExperience() {
//         List<Doctor> sortedDoctors = doctorList;
//         // sortedDoctors.sort(Comparator.comparing(Doctor::getYearsOfExperience));
//         Collections.sort(sortedDoctors);
//         return sortedDoctors;
//     }
// }




package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.service.DoctorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DoctorServiceImplArraylist  implements DoctorService {

    private static List<Doctor> doctorList = new ArrayList<>();

    @Override
    public void emptyArrayList() {
        doctorList = new ArrayList<>();
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorList;
    }

    @Override
    public Integer addDoctor(Doctor doctor) {
        doctorList.add(doctor);
        return doctorList.size();
    }

    @Override
    public List<Doctor> getDoctorSortedByExperience() {
        List<Doctor> sortedDoctors = doctorList;
        // sortedDoctors.sort(Comparator.comparing(Doctor::getYearsOfExperience));
        Collections.sort(sortedDoctors);
        return sortedDoctors;
    }

    @Override
    public void updateDoctor(Doctor doctor) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDoctor'");
    }

    @Override
    public void deleteDoctor(int doctorId) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteDoctor'");
    }
}