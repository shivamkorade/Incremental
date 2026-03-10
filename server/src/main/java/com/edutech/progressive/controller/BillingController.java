// package com.wecp.progressive.controller;

// import com.wecp.progressive.entity.Billing;
// import org.springframework.http.ResponseEntity;

// import java.util.List;

// public class BillingController {

//     public ResponseEntity<List<Billing>> getAllBills() {
//         return null;
//     }

//     public ResponseEntity<Integer> createBill(Billing billing) {
//         return null;
//     }

//     public ResponseEntity<Integer> deleteBill(Billing billing) {
//         return null;
//     }

//     public ResponseEntity<List<Billing>> getBillsByBillingID(int billingId) {
//         return null;
//     }

//     public ResponseEntity<List<Billing>> getBillsByPatient(int patientId) {
//         return null;
//     }
// }




package com.edutech.progressive.controller;

import com.edutech.progressive.entity.Billing;
import com.edutech.progressive.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @GetMapping
    public ResponseEntity<List<Billing>> getAllBills() {
        return new ResponseEntity<>(billingService.getAllBills(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> createBill(@RequestBody Billing billing) {
        return new ResponseEntity<>(billingService.createBill(billing), HttpStatus.CREATED);
    }

    @DeleteMapping("/{billingId}")
    public ResponseEntity<Void> deleteBill(@PathVariable int billingId) {
        billingService.deleteBill(billingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{billingId}")
    public ResponseEntity<Billing> getBillsByBillingId(@PathVariable int billingId) {
        //Billing bill = billingService.getBillById(billingId);
        // if (bill != null) {
        //     return new ResponseEntity<>(bill, HttpStatus.OK);
        // } else {
        //     return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        // }
        return ResponseEntity.ok(billingService.getBillById(billingId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Billing>> getBillsByPatient(@PathVariable int patientId) {
        return new ResponseEntity<>(billingService.getBillsByPatientId(patientId), HttpStatus.OK);
    }
}
