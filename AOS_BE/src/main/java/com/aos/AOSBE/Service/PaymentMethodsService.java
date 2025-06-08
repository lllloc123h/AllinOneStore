package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class PaymentMethodsService {
	@Autowired
    private PaymentMethodsRepository paymentMethodsRepository;

    public List<PaymentMethods> paymentMethodsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return paymentMethodsRepository.findAll(pageable).getContent();
    }
    @Transactional
    public PaymentMethods paymentMethodsSave(PaymentMethods paymentMethods) {
        return paymentMethodsRepository.save(paymentMethods);
    }
    public Optional<PaymentMethods> paymentMethodsFindById(int id) {
        return paymentMethodsRepository.findById(id);
    }
    @Transactional
    public void paymentMethodsDeleteById(int id) {
        paymentMethodsRepository.deleteById(id);
    }
}