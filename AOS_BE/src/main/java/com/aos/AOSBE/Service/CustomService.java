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
public class CustomService {
	@Autowired
    private CustomRepository customRepository;

    public List<Custom> customFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return customRepository.findAll(pageable).getContent();
    }
    public Custom customSave(Custom custom) {
        return customRepository.save(custom);
    }
    public Optional<Custom> customFindById(int id) {
        return customRepository.findById(id);
    }
    public void customDeleteById(int id) {
        customRepository.deleteById(id);
    }
}