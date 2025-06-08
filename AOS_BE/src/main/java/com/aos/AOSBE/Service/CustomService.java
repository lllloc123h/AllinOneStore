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
public class CustomService {
	@Autowired
    private CustomRepository customRepository;

    public List<Custom> customFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return customRepository.findAll(pageable).getContent();
    }
    @Transactional
    public Custom customSave(Custom custom) {
        return customRepository.save(custom);
    }
    public Optional<Custom> customFindById(int id) {
        return customRepository.findById(id);
    }
    @Transactional
    public void customDeleteById(int id) {
        customRepository.deleteById(id);
    }
}