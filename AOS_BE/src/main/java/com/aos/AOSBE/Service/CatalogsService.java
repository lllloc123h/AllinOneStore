package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.Catalogs;
import com.aos.AOSBE.Repository.CatalogsRepository;

@Service
public class CatalogsService {
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private CatalogsRepository catalogsRepository;

	public List<Catalogs> catalogsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Catalogs> spec = specBuilder.buildFilter(filters);
		return catalogsRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public Catalogs catalogsSave(Catalogs catalogs) {
		return catalogsRepository.save(catalogs);
	}

	public Optional<Catalogs> catalogsFindById(int id) {
		return catalogsRepository.findById(id);
	}

	@Transactional
	public void catalogsDeleteById(int id) {
		catalogsRepository.deleteById(id);
	}
}