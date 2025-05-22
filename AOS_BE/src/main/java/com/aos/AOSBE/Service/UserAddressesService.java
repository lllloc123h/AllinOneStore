package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class UserAddressesService {
	@Autowired
    private UserAddressesRepository userUserAddressesRepository;

    public List<UserAddresses> userUserAddressesFindAll() {
        return userUserAddressesRepository.findAll();
    }
    public UserAddresses userUserAddressesSave(UserAddresses userUserAddresses) {
        return userUserAddressesRepository.save(userUserAddresses);
    }
    public Optional<UserAddresses> userUserAddressesFindById(int id) {
        return userUserAddressesRepository.findById(id);
    }
    public void userUserAddressesDeleteById(int id) {
        userUserAddressesRepository.deleteById(id);
    }
}