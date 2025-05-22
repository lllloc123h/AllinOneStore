package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class ShippingMethodService {
	@Autowired
    private ShippingMethodRepository shippingShippingMethodRepository;

    public List<ShippingMethod> shippingShippingMethodFindAll() {
        return shippingShippingMethodRepository.findAll();
    }
    public ShippingMethod shippingShippingMethodSave(ShippingMethod shippingShippingMethod) {
        return shippingShippingMethodRepository.save(shippingShippingMethod);
    }
    public Optional<ShippingMethod> shippingShippingMethodFindById(int id) {
        return shippingShippingMethodRepository.findById(id);
    }
    public void shippingShippingMethodDeleteById(int id) {
        shippingShippingMethodRepository.deleteById(id);
    }
}