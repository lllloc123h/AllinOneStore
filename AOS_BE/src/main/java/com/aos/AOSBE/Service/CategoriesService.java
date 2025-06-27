package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.Categories;
import com.aos.AOSBE.Repository.CategoriesRepository;

@Service
public class CategoriesService {
	@Autowired
	private CategoriesRepository categoriesRepository;

	public List<Categories> categoriesFindAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return categoriesRepository.findAll(pageable).getContent();
	}

	public Optional<Categories> categoriesFindByName(String name) {
		return categoriesRepository.findByName(name);
	}

	@Transactional
	public Categories categoriesSave(Categories categories) {
		return categoriesRepository.save(categories);
	}

	public Optional<Categories> categoriesFindById(int id) {
		return categoriesRepository.findById(id);
	}

	@Transactional
	public void categoriesDeleteById(int id) {
		categoriesRepository.deleteById(id);
	}
}