package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.DTOS.AccountsDTOS;
import com.aos.AOSBE.DTOS.loginRequestDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Mapper.AccountsMapper;
import com.aos.AOSBE.SecurityConfig.JwtUtil;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.EmailService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AccountsAPI {
	@Autowired
	HttpServletRequest request;
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private EmailService emailService;
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AccountsMapper accountsMapper;

	@GetMapping("/admin/Accounts")
	public ResponseEntity<List<AccountsDTOS>> getAllAccountsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<AccountsDTOS> accounts = new ArrayList<AccountsDTOS>();
		accountsService.accountsFindAll(page, size).forEach(e -> {
			accounts.add(accountsMapper.mapper(e));
		});
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/admin/Accounts/{id}")
	public ResponseEntity<Accounts> getAccountsByIdApi(@PathVariable int id) {
		Accounts accounts = (Accounts) accountsService.accountsFindById(id).orElse(new Accounts());
		return ResponseEntity.ok(accounts);
	}

	@PostMapping("/admin/Accounts")
	public ResponseEntity<Accounts> addNewAccounts(@RequestBody AccountsDTOS entity) {

		Accounts saved = accountsService.accountsSave(accountsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/Accounts")
	public ResponseEntity<Accounts> updateAccounts(@RequestBody Accounts entity) {
		Accounts updated = accountsService.accountsSave(entity);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/admin/Accounts/{id}")
	public ResponseEntity<Void> deleteAccounts(@PathVariable int id) {
		accountsService.accountsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/Accounts/login")
	public ResponseEntity<?> handleLogin(@RequestBody loginRequestDTOS entity) {
		try {
			System.out.println(entity);
			System.out.println(new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword()));
			new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword());
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword()));
			UserDetails user = (UserDetails) authentication.getPrincipal();
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);
			String token = jwtUtil.generateToken(user.getUsername());
			return ResponseEntity.ok(Collections.singletonMap("token", token));

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Collections.singletonMap("error", "Invalid credentials"));
		}

	}

	@GetMapping("/Accounts/register")
	public ResponseEntity<?> handleregister() {
		emailService.sendVerificationEmail("khannps39199@gmail.com", "123456");
		return ResponseEntity.ok(Collections.singletonMap("token", "123456"));

	}

}
