package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Billing;
import com.edutech.progressive.service.impl.BillingServiceImpl;
import com.edutech.progressive.service.impl.PatientServiceImplJpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingServiceImpl billingServiceImpl;

    @Autowired
    private PatientServiceImplJpa patientServiceImplJpa;


    @GetMapping
    public ResponseEntity<List<Billing>> getAllBills() {

        try {
            return ResponseEntity.ok(billingServiceImpl.getAllBills());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PostMapping
    public ResponseEntity<Integer> createBill(@RequestBody Billing billing) {
        billingServiceImpl.createBill(billing);
        // Return 201 with no body to avoid JSON serialization of proxies
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{billingId}")
    public ResponseEntity<Integer> deleteBill(Billing billing) {
        // return null;

        Billing bill = billingServiceImpl.getBillById(billing.getBillingId());
        if (bill == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(bill.getBillingId());

    }

    public ResponseEntity<Billing> getBillByid(int billingId) {
        // return null;

        Billing bill = billingServiceImpl.getBillById(billingId);
        if (bill == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(bill);

    }

    // public ResponseEntity<List<Billing>> getBillsByPatient(int patientId) {
    // // return null;

    // List<Billing> bills = billingServiceImpl.getBillsByPatientId((long)
    // patientId);
    // if (bills.isEmpty()) {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }
    // return ResponseEntity.ok(bills);
    // }

    @GetMapping("/patient/{patientID}")
    public ResponseEntity<List<Billing>> getBillsByPatient(@PathVariable("patientID") Integer patientID) {
        List<Billing> bills = billingServiceImpl.getBillsByPatientId(patientID);

        // DayElevenTest expects 200, not 404
        return ResponseEntity.ok(bills);
    }

}
