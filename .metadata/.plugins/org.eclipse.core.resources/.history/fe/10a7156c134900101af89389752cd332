package com.aos.AOSBE.API;

import java.util.*;

import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Service.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.aos.AOSBE.DTOS.OtpDTO;
import com.aos.AOSBE.DTOS.RegisterRequestDTO;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Mapper.AccountsMapper;
import com.aos.AOSBE.SecurityConfig.JwtUtil;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.AuthoritiesService;
import com.aos.AOSBE.Service.EmailService;
import com.aos.AOSBE.Service.OTPService;

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
	private OTPService otpService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private EmailService emailService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AccountsMapper accountsMapper;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("/admin/Accounts")
	public ResponseEntity<List<AccountsDTOS>> getAllAccountsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<AccountsDTOS> accounts = new ArrayList<AccountsDTOS>();
		accountsService.accountsFindAll(page, size).forEach(e -> {
			accounts.add(accountsMapper.mapper(e));
		});
		return ResponseEntity.ok(accounts);
	}

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		System.err.println("test");

		return ResponseEntity.ok(authoritiesService.findAllByEmail("adminCUDE@gmail.com").stream()
				.map(authority -> authority.getAccounts().getEmail()).toList());
	}

	@GetMapping("/user/Accounts")
	public ResponseEntity<List<AccountsDTOS>> getAllAccountsApiUser(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<AccountsDTOS> accounts = new ArrayList<AccountsDTOS>();
		accountsService.accountsFindAll(page, size).forEach(e -> {
			accounts.add(accountsMapper.mapper(e));
		});
		accounts.stream().forEach(e -> System.out.println(e.getEmail()));
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

	@PutMapping("/admin/Accounts/{id}")
	public ResponseEntity<?> updateAccounts(@PathVariable int id, @RequestBody AccountsDTOS entity) {
		try {
			Accounts isExist = accountsService.accountsFindById(id).orElse(null);
			if (isExist != null) {
				entity.setId(isExist.getId());
				entity.setPassword(passwordEncoder.encode(entity.getPassword()));
				Accounts update = accountsMapper.mapperToObject(entity);
				accountsService.accountsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/Accounts/{id}")
	public ResponseEntity<Void> deleteAccounts(@PathVariable int id) {
		accountsService.accountsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/Accounts/login")
	public ResponseEntity<?> handleLogin(@RequestBody loginRequestDTOS entity) {
		try {
			new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword());
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(entity.getEmail(), entity.getPassword()));
			UserDetails user = (UserDetails) authentication.getPrincipal();
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);
			String token = jwtUtil.generateToken(user.getUsername());
			return ResponseEntity
					.ok(Map.of("message", "Đăng nhập thành công", "token", token, "username", user.getAuthorities()));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Sai thông tin đăng nhập"));
		}

	}

	@PostMapping("/Accounts/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
		if (accountsService.accountsFindByEmail(registerRequestDTO.getEmail()).isPresent()) {
			return ResponseEntity.badRequest().body(Map.of("message", "Email đã tồn tại !"));
		}
		return ResponseEntity.ok(Map.of("message", "Vui lòng kiểm tra OTP trong email", "OTP",
				otpService.generateOtpToRegister(30000L, registerRequestDTO)));
	}

	@PostMapping("/Accounts/verify-otp")
	public ResponseEntity<?> verifyOtp(@RequestBody OtpDTO otp) {
		try {
			if (otpService.checkOtpToRegister(otp.getOtp())) {
				return ResponseEntity.ok(Map.of("message", "Đăng ký thành công", "user",
						accountsService.registerByEmail(otpService.getRegisterDTO())));
			}
			return ResponseEntity.badRequest().body(Map.of("message", "OTP sai hoặc OTP hết hạn!"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra !"));
		}
	}
	
	@PutMapping("/Accounts/change-password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTOS dto) {
	    try {
	        accountsService.changePassword(dto);
	        return ResponseEntity.ok("Đổi mật khẩu thành công");
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body("Lỗi hệ thống");
	    }
	}
	

//	@PutMapping("/Accounts/profile")
//	public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileDTO dto) {
//    try {
//        accountsService.updateProfile(dto);
//        return ResponseEntity.ok(Map.of("message", "Cập nhật thông tin thành công"));
//    } catch (RuntimeException e) {
//        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
//    } catch (Exception e) {
//        return ResponseEntity.internalServerError().body(Map.of("message", "Lỗi hệ thống"));
//    }
//}

}
