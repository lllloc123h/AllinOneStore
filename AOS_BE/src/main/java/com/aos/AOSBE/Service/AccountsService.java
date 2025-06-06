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
@Service
public class AccountsService {
	@Autowired
    private AccountsRepository accountsRepository;

    public List<Accounts> accountsFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return accountsRepository.findAll(pageable).getContent();
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
    public Accounts accountsFindByEmail(String email) {
		return accountsRepository.findByEmail(email).get();
    }
}