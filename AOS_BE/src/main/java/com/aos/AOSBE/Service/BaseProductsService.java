package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class BaseProductsService {
	@Autowired
    private BaseProductsRepository baseBaseProductsRepository;

    public List<BaseProducts> baseBaseProductsFindAll() {
        return baseBaseProductsRepository.findAll();
    }
    public BaseProducts baseBaseProductsSave(BaseProducts baseBaseProducts) {
        return baseBaseProductsRepository.save(baseBaseProducts);
    }
    public Optional<BaseProducts> baseBaseProductsFindById(int id) {
        return baseBaseProductsRepository.findById(id);
    }
    public void baseBaseProductsDeleteById(int id) {
        baseBaseProductsRepository.deleteById(id);
    }
}