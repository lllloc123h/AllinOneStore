package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class VariantsService {
	@Autowired
    private VariantsRepository variantsRepository;

    public List<Variants> variantsFindAll() {
        return variantsRepository.findAll();
    }
    public Variants variantsSave(Variants variants) {
        return variantsRepository.save(variants);
    }
    public Optional<Variants> variantsFindById(int id) {
        return variantsRepository.findById(id);
    }
    public void variantsDeleteById(int id) {
        variantsRepository.deleteById(id);
    }
}