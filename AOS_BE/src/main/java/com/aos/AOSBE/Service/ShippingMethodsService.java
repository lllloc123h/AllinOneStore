package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class ShippingMethodsService {
	@Autowired
    private ShippingMethodsRepository shippingShippingMethodsRepository;

    public List<ShippingMethods> shippingShippingMethodsFindAll() {
        return shippingShippingMethodsRepository.findAll();
    }
    public ShippingMethods shippingShippingMethodsSave(ShippingMethods shippingShippingMethods) {
        return shippingShippingMethodsRepository.save(shippingShippingMethods);
    }
    public Optional<ShippingMethods> shippingShippingMethodsFindById(int id) {
        return shippingShippingMethodsRepository.findById(id);
    }
    public void shippingShippingMethodsDeleteById(int id) {
        shippingShippingMethodsRepository.deleteById(id);
    }
}