package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class UsersService {
	@Autowired
    private UsersRepository usersRepository;

    public List<Users> usersFindAll() {
        return usersRepository.findAll();
    }
    public Users usersSave(Users users) {
        return usersRepository.save(users);
    }
    public Optional<Users> usersFindById(int id) {
        return usersRepository.findById(id);
    }
    public void usersDeleteById(int id) {
        usersRepository.deleteById(id);
    }
}