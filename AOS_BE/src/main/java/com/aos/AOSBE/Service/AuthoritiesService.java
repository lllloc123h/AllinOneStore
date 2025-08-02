package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Repository.AuthoritiesRepository;

@Service
public class AuthoritiesService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private AuthoritiesRepository authoritiesRepository;

	public Page<Authorities> authoritiesFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Authorities> spec = specBuilder.buildFilter(filters);
		return authoritiesRepository.findAll(spec, pageable);
	}

	public Authorities authoritiesSave(Authorities authorities) {
		return authoritiesRepository.save(authorities);
	}

	public Optional<Authorities> authoritiesFindById(int id) {
		return authoritiesRepository.findById(id);
	}

	public List<Authorities> authoritiesFindByAccountsId(int id) {
		return authoritiesRepository.findAllByAccountsId(id);
	}

	public List<String> authoritiesFindRolesByAccountsEmail(String email) {
		return authoritiesRepository.findAllRoleByAccountEmail(email);
	}

	public void authoritiesDeleteById(int id) {
		authoritiesRepository.deleteById(id);
	}

	public List<Authorities> findAllByEmail(String email) {
		return authoritiesRepository.findAllByEmail(email);
	}
}