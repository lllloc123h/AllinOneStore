package com.aos.AOSBE.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Service.AccountsService;

@RestController
public class DashBoardAPI {


	@Autowired
	private AccountsService accountsService;
	
	@GetMapping("/Accounts")
	public ResponseEntity<List<Accounts>> getAllAccountsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<Accounts> accounts = accountsService.accountsFindAll(page,size);
		return ResponseEntity.ok(accounts);
	}
}
