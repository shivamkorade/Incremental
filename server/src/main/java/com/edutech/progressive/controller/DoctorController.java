// package com.wecp.progressive.controller;

// import com.wecp.progressive.entity.Doctor;
// import com.wecp.progressive.service.DoctorService;
// import com.wecp.progressive.service.impl.DoctorServiceImplJpa;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import java.util.List;
// @RestController
// @RequestMapping("/doctor")
// public class DoctorController {
//     @Autowired
//     DoctorServiceImplJpa dr;
//     public DoctorController(DoctorServiceImplJpa dr) {
//         this.dr = dr;
//     }
//     @GetMapping
//     public ResponseEntity<List<Doctor>> getAllDoctors() throws Exception {
//        return new ResponseEntity<>(dr.getAllDoctors(),HttpStatus.OK);
//     }
//     @GetMapping("/{id}")
//     public ResponseEntity<Doctor> getDoctorById(@PathVariable int doctorId) throws Exception {
//        return new ResponseEntity<>(dr.getDoctorById(doctorId),HttpStatus.OK);
//     }
//     @PostMapping
//     public ResponseEntity<Integer> addDoctor(@RequestBody Doctor doctor) throws Exception {
//         return new ResponseEntity<>(dr.addDoctor(doctor),HttpStatus.CREATED);
//     }
//     @PutMapping("/{id}")
//     public ResponseEntity<Void> updateDoctor(@PathVariable int id, @RequestBody Doctor doctor) throws Exception {
//         dr.updateDoctor(doctor);
//         return new ResponseEntity<>(HttpStatus.OK);
//     }
//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteDoctor(@PathVariable int id) throws Exception {
//         dr.deleteDoctor(id);
//         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//     }
//     @GetMapping("/{experience}")
//     public ResponseEntity<List<Doctor>> getDoctorSortedByExperience() throws Exception {
//          dr.getDoctorSortedByExperience();
//          return new ResponseEntity<>(HttpStatus.OK);
//     }
// }



package com.edutech.progressive.controller;

import com.edutech.progressive.dto.DoctorDTO;
import com.edutech.progressive.entity.Doctor;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.service.impl.DoctorServiceImplJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    DoctorServiceImplJpa doctorServiceImplJpa;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        try {
            List<Doctor> doctorList = doctorServiceImplJpa.getAllDoctors();
            return new ResponseEntity<>(doctorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<?> getDoctorById(@PathVariable int doctorId) {
        try {
            Doctor doctor = doctorServiceImplJpa.getDoctorById(doctorId);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> addDoctor(@RequestBody Doctor doctor) {
        try {
            int doctorId = doctorServiceImplJpa.addDoctor(doctor);
            return new ResponseEntity<>(doctorId, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{doctorId}")
    public ResponseEntity<?> updateDoctor(@PathVariable int doctorId, @RequestBody DoctorDTO doctor) {
        try {
            doctor.setDoctorId(doctorId);
            doctorServiceImplJpa.modifyDoctorDetails(doctor);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<?> deleteDoctor(@PathVariable int doctorId) {
        try {
            doctorServiceImplJpa.deleteDoctor(doctorId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/experience")
    public ResponseEntity<?> getDoctorSortedByExperience() {
        try {
            List<Doctor> doctorList = doctorServiceImplJpa.getDoctorSortedByExperience();
            return new ResponseEntity<>(doctorList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}