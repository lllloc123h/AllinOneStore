package com.aos.AOSBE.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Service.AccountsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountAPI {
	@Autowired
	private AccountsService accountsService;

	@GetMapping("/Accounts")
	public ResponseEntity<List<Accounts>> getAllAccountsApi() {
		List<Accounts> accounts = accountsService.accountsFindAll();
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/Accounts/{id}")
	public ResponseEntity<Accounts> getAccountsByIdApi(@PathVariable int id) {
		Accounts accounts =(Accounts) accountsService.accountsFindById(id).orElse(new Accounts());
		return ResponseEntity.ok(accounts);
	}
	@PostMapping("/Accounts")
	public ResponseEntity<Accounts> addNewAccount(@RequestBody Accounts entity) {
	    Accounts saved = accountsService.accountsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Accounts")
	public ResponseEntity<Accounts> updateAccount(@RequestBody Accounts entity) {
	    Accounts updated = accountsService.accountsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Accounts/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable int id) {
	    accountsService.accountsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
