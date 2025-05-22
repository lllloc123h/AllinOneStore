package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class CatalogsService {
	@Autowired
    private CatalogsRepository catalogsRepository;

    public List<Catalogs> catalogsFindAll() {
        return catalogsRepository.findAll();
    }
    public Catalogs catalogsSave(Catalogs catalogs) {
        return catalogsRepository.save(catalogs);
    }
    public Optional<Catalogs> catalogsFindById(String id) {
        return catalogsRepository.findById(id);
    }
    public void catalogsDeleteById(String id) {
        catalogsRepository.deleteById(id);
    }
}