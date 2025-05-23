package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class ComponentsService {
	@Autowired
    private ComponentsRepository componentsRepository;

    public List<Components> componentsFindAll() {
        return componentsRepository.findAll();
    }
    public Components componentsSave(Components components) {
        return componentsRepository.save(components);
    }
    public Optional<Components> componentsFindById(int id) {
        return componentsRepository.findById(id);
    }
    public void componentsDeleteById(int id) {
        componentsRepository.deleteById(id);
    }
}