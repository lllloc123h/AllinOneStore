package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class ExtendValuesService {
	@Autowired
    private ExtendValuesRepository extendExtendValuesRepository;

    public List<ExtendValues> extendExtendValuesFindAll() {
        return extendExtendValuesRepository.findAll();
    }
    public ExtendValues extendExtendValuesSave(ExtendValues extendExtendValues) {
        return extendExtendValuesRepository.save(extendExtendValues);
    }
    public Optional<ExtendValues> extendExtendValuesFindById(int id) {
        return extendExtendValuesRepository.findById(id);
    }
    public void extendExtendValuesDeleteById(int id) {
        extendExtendValuesRepository.deleteById(id);
    }
}