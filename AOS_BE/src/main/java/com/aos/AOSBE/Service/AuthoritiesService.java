package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class AuthoritiesService {
	@Autowired
    private AuthoritiesRepository authoritiesRepository;

    public List<Authorities> authoritiesFindAll() {
        return authoritiesRepository.findAll();
    }
    public Authorities authoritiesSave(Authorities authorities) {
        return authoritiesRepository.save(authorities);
    }
    public Optional<Authorities> authoritiesFindById(int id) {
        return authoritiesRepository.findById(id);
    }
    public void authoritiesDeleteById(int id) {
        authoritiesRepository.deleteById(id);
    }
}