package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class RolesService {
	@Autowired
    private RolesRepository rolesRepository;

    public List<Roles> rolesFindAll() {
        return rolesRepository.findAll();
    }
    public Roles rolesSave(Roles roles) {
        return rolesRepository.save(roles);
    }
    public Optional<Roles> rolesFindById(int id) {
        return rolesRepository.findById(id);
    }
    public void rolesDeleteById(int id) {
        rolesRepository.deleteById(id);
    }
}