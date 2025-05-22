package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class VariantValuesService {
	@Autowired
    private VariantValuesRepository variantVariantValuesRepository;

    public List<VariantValues> variantVariantValuesFindAll() {
        return variantVariantValuesRepository.findAll();
    }
    public VariantValues variantVariantValuesSave(VariantValues variantVariantValues) {
        return variantVariantValuesRepository.save(variantVariantValues);
    }
    public Optional<VariantValues> variantVariantValuesFindById(int id) {
        return variantVariantValuesRepository.findById(id);
    }
    public void variantVariantValuesDeleteById(int id) {
        variantVariantValuesRepository.deleteById(id);
    }
}