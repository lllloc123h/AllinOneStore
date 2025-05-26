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
public class ReturnsService {
	@Autowired
    private ReturnsRepository returnsRepository;

    public List<Returns> returnsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return returnsRepository.findAll(pageable).getContent();
    }
    public Returns returnsSave(Returns returns) {
        return returnsRepository.save(returns);
    }
    public Optional<Returns> returnsFindById(int id) {
        return returnsRepository.findById(id);
    }
    public void returnsDeleteById(int id) {
        returnsRepository.deleteById(id);
    }
}