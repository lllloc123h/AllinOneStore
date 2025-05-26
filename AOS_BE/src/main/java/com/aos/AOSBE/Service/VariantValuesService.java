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
@Service
public class VariantValuesService {
	@Autowired
    private VariantValuesRepository variantValuesRepository;

    public List<VariantValues> variantValuesFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return variantValuesRepository.findAll(pageable).getContent();
    }
    public VariantValues variantValuesSave(VariantValues variantValues) {
        return variantValuesRepository.save(variantValues);
    }
    public Optional<VariantValues> variantValuesFindById(int id) {
        return variantValuesRepository.findById(id);
    }
    public void variantValuesDeleteById(int id) {
        variantValuesRepository.deleteById(id);
    }
}