package com.edutech.progressive.service.impl;

import com.edutech.progressive.entity.Billing;
import com.edutech.progressive.repository.BillingRepository;
import com.edutech.progressive.repository.PatientRepository;
import com.edutech.progressive.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillingServiceImpl implements BillingService {

    @Autowired
    private PatientRepository pr;

    @Autowired
    private BillingRepository billingRepository;



    @Override
    public List<Billing> getAllBills() {
        return billingRepository.findAll();
    }

    @Override
    public Integer createBill(Billing billing) {
        return billingRepository.save(billing).getBillingId();
    }

    @Override
    public void deleteBill(int billingId) {
        billingRepository.deleteById(billingId);
    } 

    @Override
    public Billing getBillById(int billingId) {
        return billingRepository.findById(billingId).orElse(null);
    }

    @Override
    public List<Billing> getBillsByPatientId(int patientId) {
        return billingRepository.findByPatient_PatientId(patientId);
    }
}
