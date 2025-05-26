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
@Service
public class CatalogsService {
	@Autowired
    private CatalogsRepository catalogsRepository;

    public List<Catalogs> catalogsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return catalogsRepository.findAll(pageable).getContent();
    }
    public Catalogs catalogsSave(Catalogs catalogs) {
        return catalogsRepository.save(catalogs);
    }
    public Optional<Catalogs> catalogsFindById(int id) {
        return catalogsRepository.findById(id);
    }
    public void catalogsDeleteById(int id) {
        catalogsRepository.deleteById(id);
    }
}