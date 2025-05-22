package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class AccountsService {
	@Autowired
    private AccountsRepository accountsRepository;

    public List<Accounts> accountsFindAll() {
        return accountsRepository.findAll();
    }
    public Accounts accountsSave(Accounts accounts) {
        return accountsRepository.save(accounts);
    }
    public Optional<Accounts> accountsFindById(int id) {
        return accountsRepository.findById(id);
    }
    public void accountsDeleteById(int id) {
        accountsRepository.deleteById(id);
    }
}