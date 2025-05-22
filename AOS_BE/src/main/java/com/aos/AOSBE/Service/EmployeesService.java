package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class EmployeesService {
	@Autowired
    private EmployeesRepository employeesRepository;

    public List<Employees> employeesFindAll() {
        return employeesRepository.findAll();
    }
    public Employees employeesSave(Employees employees) {
        return employeesRepository.save(employees);
    }
    public Optional<Employees> employeesFindById(String id) {
        return employeesRepository.findById(id);
    }
    public void employeesDeleteById(String id) {
        employeesRepository.deleteById(id);
    }
}