package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class ExtendInforsService {
	@Autowired
    private ExtendInforsRepository extendExtendInforsRepository;

    public List<ExtendInfors> extendExtendInforsFindAll() {
        return extendExtendInforsRepository.findAll();
    }
    public ExtendInfors extendExtendInforsSave(ExtendInfors extendExtendInfors) {
        return extendExtendInforsRepository.save(extendExtendInfors);
    }
    public Optional<ExtendInfors> extendExtendInforsFindById(String id) {
        return extendExtendInforsRepository.findById(id);
    }
    public void extendExtendInforsDeleteById(String id) {
        extendExtendInforsRepository.deleteById(id);
    }
}