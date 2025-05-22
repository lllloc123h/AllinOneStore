package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class UserInforsService {
	@Autowired
    private UserInforsRepository userUserInforsRepository;

    public List<UserInfors> userUserInforsFindAll() {
        return userUserInforsRepository.findAll();
    }
    public UserInfors userUserInforsSave(UserInfors userUserInfors) {
        return userUserInforsRepository.save(userUserInfors);
    }
    public Optional<UserInfors> userUserInforsFindById(String id) {
        return userUserInforsRepository.findById(id);
    }
    public void userUserInforsDeleteById(String id) {
        userUserInforsRepository.deleteById(id);
    }
}