package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class UserAddressesService {
	@Autowired
    private UserAddressesRepository userAddressesRepository;

    public List<UserAddresses> userAddressesFindAll() {
        return userAddressesRepository.findAll();
    }
    public UserAddresses userAddressesSave(UserAddresses userAddresses) {
        return userAddressesRepository.save(userAddresses);
    }
    public Optional<UserAddresses> userAddressesFindById(int id) {
        return userAddressesRepository.findById(id);
    }
    public void userAddressesDeleteById(int id) {
        userAddressesRepository.deleteById(id);
    }
}