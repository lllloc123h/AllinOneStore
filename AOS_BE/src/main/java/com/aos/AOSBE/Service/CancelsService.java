package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class CancelsService {
	@Autowired
    private CancelsRepository cancelsRepository;

    public List<Cancels> cancelsFindAll() {
        return cancelsRepository.findAll();
    }
    public Cancels cancelsSave(Cancels cancels) {
        return cancelsRepository.save(cancels);
    }
    public Optional<Cancels> cancelsFindById(int id) {
        return cancelsRepository.findById(id);
    }
    public void cancelsDeleteById(int id) {
        cancelsRepository.deleteById(id);
    }
}