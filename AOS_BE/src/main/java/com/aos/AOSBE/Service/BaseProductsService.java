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
    private BaseProductsRepository baseProductsRepository;

    public List<BaseProducts> baseProductsFindAll() {
        return baseProductsRepository.findAll();
    }
    public BaseProducts baseProductsSave(BaseProducts baseProducts) {
        return baseProductsRepository.save(baseProducts);
    }
    public Optional<BaseProducts> baseProductsFindById(int id) {
        return baseProductsRepository.findById(id);
    }
    public void baseProductsDeleteById(int id) {
        baseProductsRepository.deleteById(id);
    }
}