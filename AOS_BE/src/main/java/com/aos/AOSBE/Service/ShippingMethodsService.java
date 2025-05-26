package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class ShippingMethodsService {
	@Autowired
    private ShippingMethodsRepository shippingMethodsRepository;

    public List<ShippingMethods> shippingMethodsFindAll() {
        return shippingMethodsRepository.findAll();
    }
    public ShippingMethods shippingMethodsSave(ShippingMethods shippingMethods) {
        return shippingMethodsRepository.save(shippingMethods);
    }
    public Optional<ShippingMethods> shippingMethodsFindById(int id) {
        return shippingMethodsRepository.findById(id);
    }
    public void shippingMethodsDeleteById(int id) {
        shippingMethodsRepository.deleteById(id);
    }
}