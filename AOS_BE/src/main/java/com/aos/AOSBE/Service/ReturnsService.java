package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class ReturnsService {
	@Autowired
    private ReturnsRepository returnsRepository;

    public List<Returns> returnsFindAll() {
        return returnsRepository.findAll();
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