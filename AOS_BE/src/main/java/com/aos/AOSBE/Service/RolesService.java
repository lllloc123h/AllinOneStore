package com.aos.AOSBE.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.Roles;
import com.aos.AOSBE.Repository.RolesRepository;

@Service
public class RolesService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private RolesRepository rolesRepository;

	public Page<Roles> rolesFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Roles> spec = specBuilder.buildFilter(filters);
		return rolesRepository.findAll(spec, pageable);
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