package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class CouponsService {
	@Autowired
    private CouponsRepository couponsRepository;

    public List<Coupons> couponsFindAll() {
        return couponsRepository.findAll();
    }
    public Coupons couponsSave(Coupons coupons) {
        return couponsRepository.save(coupons);
    }
    public Optional<Coupons> couponsFindById(int id) {
        return couponsRepository.findById(id);
    }
    public void couponsDeleteById(int id) {
        couponsRepository.deleteById(id);
    }
}