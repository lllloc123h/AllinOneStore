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
public class VariantsService {
	@Autowired
    private VariantsRepository variantsRepository;

    public List<Variants> variantsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return variantsRepository.findAll(pageable).getContent();
    }
    @Transactional
    public Variants variantsSave(Variants variants) {
        return variantsRepository.save(variants);
    }
    public Optional<Variants> variantsFindById(int id) {
        return variantsRepository.findById(id);
    }
    @Transactional
    public void variantsDeleteById(int id) {
        variantsRepository.deleteById(id);
    }
}