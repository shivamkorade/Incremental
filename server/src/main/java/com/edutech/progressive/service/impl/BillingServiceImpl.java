package com.edutech.progressive.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.progressive.entity.Billing;
import com.edutech.progressive.entity.Patient;
import com.edutech.progressive.repository.BillingRepository;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.service.BillingService;

@Service
public class BillingServiceImpl implements BillingService {

    // @Autowired
    private BillingRepository billingRepository;

    // @Autowired
    private PatientRepository patientRepository;

    public BillingServiceImpl(BillingRepository billingRepository, PatientRepository patientRepository) {
        this.billingRepository = billingRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Billing> getAllBills() {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getAllBills'");
        return billingRepository.findAll();
    }

    @Override
    public Billing getBillById(Integer billingId) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getBillById'");
        return billingRepository.findById(billingId).orElse(null);
    }

    @Override
    @Transactional
    public Integer createBill(Billing billing) {

        if (billing == null) {
            throw new IllegalArgumentException("Bill is required");
        }

        Patient incoming = billing.getPatient();
        if (incoming == null || incoming.getPatientId() == null) {
            // If this ever triggers, JSON binding didn’t populate "patient"
            throw new IllegalArgumentException("Patient is required");
        }

        Patient managedPatient = patientRepository.getReferenceById(incoming.getPatientId());
        billing.setPatient(managedPatient);

        billingRepository.save(billing);

        return billing.getBillingId();

    }

    @Override
    public void deleteBill(Integer billingId) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'deleteBill'");
        billingRepository.deleteById(billingId);
    }

    @Override
    public List<Billing> getBillsByPatientId(Integer patientId) {
        // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method
        // 'getBillsByPatientId'");
        return billingRepository.findByPatient_PatientId(patientId);
    }

}