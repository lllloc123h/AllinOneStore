package com.aos.AOSBE.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Service.AccountsService;

@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountAPI {
	@Autowired
    private  AccountsService accountsService;

    @GetMapping("/Accounts")
    public ResponseEntity<List<Accounts>> getAllAccountsApi() {
        List<Accounts> accounts = accountsService.accountsFindAll();
        return ResponseEntity.ok(accounts);
    }
}

