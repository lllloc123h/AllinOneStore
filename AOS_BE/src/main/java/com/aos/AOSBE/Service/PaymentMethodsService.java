package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class PaymentMethodsService {
	@Autowired
    private PaymentMethodsRepository paymentPaymentMethodsRepository;

    public List<PaymentMethods> paymentPaymentMethodsFindAll() {
        return paymentPaymentMethodsRepository.findAll();
    }
    public PaymentMethods paymentPaymentMethodsSave(PaymentMethods paymentPaymentMethods) {
        return paymentPaymentMethodsRepository.save(paymentPaymentMethods);
    }
    public Optional<PaymentMethods> paymentPaymentMethodsFindById(int id) {
        return paymentPaymentMethodsRepository.findById(id);
    }
    public void paymentPaymentMethodsDeleteById(int id) {
        paymentPaymentMethodsRepository.deleteById(id);
    }
}