package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.aos.AOSBE.DTOS.ChangePasswordDTOS;
import com.aos.AOSBE.DTOS.OtpSessionData;
import com.aos.AOSBE.DTOS.RegisterRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountsService {
	@Autowired
    private AccountsRepository accountsRepository;
    @Autowired
    private AuthoritiesRepository authorityRepository;
    @Autowired
    private RolesRepository rolesRepository;

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
        accounts.setPassword(new BCryptPasswordEncoder().encode(registerRequestDTO.getPassword()));
        accounts.setPhone(registerRequestDTO.getPhone());
        accounts.setFullname(registerRequestDTO.getFullname());
        Authorities authority = new Authorities();
        accounts = accountsRepository.save(accounts);
        authority.setAccounts(accounts);
        authority.setRoles(rolesRepository.findByName("USER").get());
        authorityRepository.save(authority);
        return accountsRepository.save(accounts);
    }
    @Transactional
    public void changePassword(ChangePasswordDTOS dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Accounts account = accountsRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new RuntimeException("Mật khẩu mới và xác nhận không khớp");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        account.setPassword(encoder.encode(dto.getNewPassword()));
        accountsRepository.save(account);
    }


}