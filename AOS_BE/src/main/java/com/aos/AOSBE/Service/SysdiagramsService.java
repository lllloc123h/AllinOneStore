package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class SysdiagramsService {
	@Autowired
    private SysdiagramsRepository sysdiagramsRepository;

    public List<Sysdiagrams> sysdiagramsFindAll() {
        return sysdiagramsRepository.findAll();
    }
    public Sysdiagrams sysdiagramsSave(Sysdiagrams sysdiagrams) {
        return sysdiagramsRepository.save(sysdiagrams);
    }
    public Optional<Sysdiagrams> sysdiagramsFindById(String id) {
        return sysdiagramsRepository.findById(id);
    }
    public void sysdiagramsDeleteById(String id) {
        sysdiagramsRepository.deleteById(id);
    }
}