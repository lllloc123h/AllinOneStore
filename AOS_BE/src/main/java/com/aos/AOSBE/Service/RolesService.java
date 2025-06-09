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
public class RolesService {
	@Autowired
    private RolesRepository rolesRepository;

    public List<Roles> rolesFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return rolesRepository.findAll(pageable).getContent();
    }
    @Transactional
    public Roles rolesSave(Roles roles) {
        return rolesRepository.save(roles);
    }
    public Optional<Roles> rolesFindById(int id) {
        return rolesRepository.findById(id);
    }
    @Transactional
    public void rolesDeleteById(int id) {
        rolesRepository.deleteById(id);
    }
}