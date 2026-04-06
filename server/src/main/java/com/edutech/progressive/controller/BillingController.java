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
        return ResponseEntity.ok(billingService.getBillById(billingId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Billing>> getBillsByPatient(@PathVariable int patientId) {
        return new ResponseEntity<>(billingService.getBillsByPatientId(patientId), HttpStatus.OK);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<?> getBillByAppointment(@PathVariable int appointmentId) {
        List<Billing> bills = billingService.getAllBills();
        return bills.stream()
                .filter(b -> b.getAppointmentId() != null && b.getAppointmentId() == appointmentId)
                .findFirst()
                .<ResponseEntity<?>>map(b -> ResponseEntity.ok(b))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{billingId}")
    public ResponseEntity<?> updateBill(@PathVariable int billingId, @RequestBody Billing billing) {
        Billing existing = billingService.getBillById(billingId);
        if (existing == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if (billing.getStatus() != null)
            existing.setStatus(billing.getStatus());
        if (billing.getPaymentMethod() != null)
            existing.setPaymentMethod(billing.getPaymentMethod());
        if (billing.getAmount() > 0)
            existing.setAmount(billing.getAmount());
        billingService.createBill(existing);
        return new ResponseEntity<>(existing, HttpStatus.OK);
    }
}