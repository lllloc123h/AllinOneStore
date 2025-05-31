package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Mapper.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountsAPI {
	@Autowired
	private AccountsService accountsService;
	private AccountsMapper accountsMapper=new AccountsMapper();

	@GetMapping("/Accounts")
	public ResponseEntity<List<AccountsDTOS>> getAllAccountsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<AccountsDTOS> accounts = new ArrayList<AccountsDTOS>();
		accountsService.accountsFindAll(page, size).forEach(e -> {
			accounts.add(accountsMapper.mapper(e));
		});
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/Accounts/{id}")
	public ResponseEntity<Accounts> getAccountsByIdApi(@PathVariable int id) {
		Accounts accounts =(Accounts)accountsService.accountsFindById(id).orElse(new Accounts());
		return ResponseEntity.ok(accounts);
	}
	@PostMapping("/Accounts")
	public ResponseEntity<Accounts> addNewAccounts(@RequestBody Accounts entity) {
	    Accounts saved = accountsService.accountsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Accounts")
	public ResponseEntity<Accounts> updateAccounts(@RequestBody Accounts entity) {
	    Accounts updated = accountsService.accountsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Accounts/{id}")
	public ResponseEntity<Void> deleteAccounts(@PathVariable int id) {
	    accountsService.accountsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
