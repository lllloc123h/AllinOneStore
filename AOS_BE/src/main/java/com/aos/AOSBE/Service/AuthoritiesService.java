package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class AuthoritiesService {
	@Autowired
    private AuthoritiesRepository authoritiesRepository;

    public List<Authorities> authoritiesFindAll() {
        return authoritiesRepository.findAll();
    }
    public Authorities authoritiesSave(Authorities authorities) {
        return authoritiesRepository.save(authorities);
    }
    public Optional<Authorities> authoritiesFindById(String id) {
        return authoritiesRepository.findById(id);
    }
    public void authoritiesDeleteById(String id) {
        authoritiesRepository.deleteById(id);
    }
}