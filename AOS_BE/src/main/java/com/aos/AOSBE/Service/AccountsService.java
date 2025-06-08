package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.aos.AOSBE.DTOS.OtpSessionData;
import com.aos.AOSBE.DTOS.RegisterRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<Accounts> accountsFindByEmail(String email) {
		return accountsRepository.findByEmail(email);
    }
    @Transactional
    public Accounts registerByEmail(RegisterRequestDTO registerRequestDTO) {
        Accounts accounts = new Accounts();
        accounts.setEmail(registerRequestDTO.getEmail());
        accounts.setPassword(registerRequestDTO.getPassword());
        accounts.setFullname(registerRequestDTO.getFullname());
        return accountsRepository.save(accounts);
    }


}