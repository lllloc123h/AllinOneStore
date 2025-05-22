package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class SuppliersService {
	@Autowired
    private SuppliersRepository suppliersRepository;

    public List<Suppliers> suppliersFindAll() {
        return suppliersRepository.findAll();
    }
    public Suppliers suppliersSave(Suppliers suppliers) {
        return suppliersRepository.save(suppliers);
    }
    public Optional<Suppliers> suppliersFindById(int id) {
        return suppliersRepository.findById(id);
    }
    public void suppliersDeleteById(int id) {
        suppliersRepository.deleteById(id);
    }
}