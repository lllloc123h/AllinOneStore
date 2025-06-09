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
import jakarta.transaction.Transactional;
@Service
public class CancelsService {
	@Autowired
    private CancelsRepository cancelsRepository;

    public List<Cancels> cancelsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return cancelsRepository.findAll(pageable).getContent();
    }
    @Transactional
    public Cancels cancelsSave(Cancels cancels) {
        return cancelsRepository.save(cancels);
    }
    public Optional<Cancels> cancelsFindById(int id) {
        return cancelsRepository.findById(id);
    }
    @Transactional
    public void cancelsDeleteById(int id) {
        cancelsRepository.deleteById(id);
    }
}