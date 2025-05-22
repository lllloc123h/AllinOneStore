package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class PaymentMethodService {
	@Autowired
    private PaymentMethodRepository paymentPaymentMethodRepository;

    public List<PaymentMethod> paymentPaymentMethodFindAll() {
        return paymentPaymentMethodRepository.findAll();
    }
    public PaymentMethod paymentPaymentMethodSave(PaymentMethod paymentPaymentMethod) {
        return paymentPaymentMethodRepository.save(paymentPaymentMethod);
    }
    public Optional<PaymentMethod> paymentPaymentMethodFindById(int id) {
        return paymentPaymentMethodRepository.findById(id);
    }
    public void paymentPaymentMethodDeleteById(int id) {
        paymentPaymentMethodRepository.deleteById(id);
    }
}