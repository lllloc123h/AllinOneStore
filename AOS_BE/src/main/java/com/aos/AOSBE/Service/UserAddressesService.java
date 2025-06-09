package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserAddressesService {
	@Autowired
    private UserAddressesRepository userAddressesRepository;

    public List<UserAddresses> userAddressesFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return userAddressesRepository.findAll(pageable).getContent();
    }
    @Transactional
    public UserAddresses userAddressesSave(UserAddresses userAddresses) {
        return userAddressesRepository.save(userAddresses);
    }
    public Optional<UserAddresses> userAddressesFindById(int id) {
        return userAddressesRepository.findById(id);
    }
    @Transactional
    public void userAddressesDeleteById(int id) {
        userAddressesRepository.deleteById(id);
    }
}