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
public class ShippingMethodsService {
	@Autowired
    private ShippingMethodsRepository shippingMethodsRepository;

    public List<ShippingMethods> shippingMethodsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return shippingMethodsRepository.findAll(pageable).getContent();
    }
    @Transactional
    public ShippingMethods shippingMethodsSave(ShippingMethods shippingMethods) {
        return shippingMethodsRepository.save(shippingMethods);
    }
    public Optional<ShippingMethods> shippingMethodsFindById(int id) {
        return shippingMethodsRepository.findById(id);
    }
    @Transactional
    public void shippingMethodsDeleteById(int id) {
        shippingMethodsRepository.deleteById(id);
    }
}