package com.aos.AOSBE.API;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

import com.aos.AOSBE.DTOS.AccountProfileDTO;
import com.aos.AOSBE.DTOS.AccountsDTOS;
import com.aos.AOSBE.DTOS.ChangePasswordDTOS;
import com.aos.AOSBE.DTOS.ForgotPasswordDTO;
import com.aos.AOSBE.DTOS.OtpDTO;
import com.aos.AOSBE.DTOS.RegisterRequestDTO;
import com.aos.AOSBE.DTOS.ResetPasswordDTO;
import com.aos.AOSBE.DTOS.UpdateProfileDTO;
import com.aos.AOSBE.DTOS.VerifyOtpDTO;
import com.aos.AOSBE.DTOS.loginRequestDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Mapper.AccountsMapper;
import com.aos.AOSBE.SecurityConfig.JwtUtil;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.AuthoritiesService;
import com.aos.AOSBE.Service.CartItemsService;
import com.aos.AOSBE.Service.EmailService;
import com.aos.AOSBE.Service.OTPService;
import com.aos.AOSBE.Service.OtpStore;

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
	@Autowired
	private CartItemsService cartItemsService;

	@GetMapping("/admin/Accounts")
	public ResponseEntity<?> getAllAccountsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<Accounts> pageResult = accountsService.accountsFindAll(page, size, filters);
		List<AccountsDTOS> accounts = pageResult.getContent().stream().map(accountsMapper::mapperWithPasswordHidden)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", accounts);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/test")
	public ResponseEntity<?> test() {
		return ResponseEntity.ok(authoritiesService.findAllByEmail("adminCUDE@gmail.com").stream()
				.map(authority -> authority.getAccounts().getEmail()).toList());
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
				if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
					// Only encode if the password is new or changed
					if (!entity.getPassword().equals(isExist.getPassword())) {
						entity.setPassword(passwordEncoder.encode(entity.getPassword()));
					} else {
						entity.setPassword(isExist.getPassword());
					}
				} else {
					// Keep existing password if not provided
					entity.setPassword(isExist.getPassword());
				}

				Accounts update = accountsMapper.mapperToObject(entity);
				accountsService.accountsSave(update);
				return ResponseEntity.ok().body(Map.of("message", "Update successfully", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
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
			return ResponseEntity.ok(Map.of("message", "Đăng nhập thành công", "token", token, "cartSize",
					cartItemsService.cartItemsFindAccounts(entity.getEmail()).stream().reduce(0,
							(a, b) -> a + b.getQty(), Integer::sum)));
		} catch (AuthenticationException e) {
			e.printStackTrace();
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
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra !"));
		}
	}

	@PutMapping("/Accounts/change-password")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTOS dto) {
	    try {
	        accountsService.changePassword(dto);
	        return ResponseEntity.ok(Map.of(
	            "status", "success",
	            "message", "Đổi mật khẩu thành công"
	        ));
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(Map.of(
	            "status", "error",
	            "message", e.getMessage()
	        ));
	    } catch (Exception e) {
	        return ResponseEntity.internalServerError().body(Map.of(
	            "status", "error",
	            "message", "Lỗi hệ thống"
	        ));
	    }
	}

	@PutMapping("/admin/Accounts/ResetPassword/{email}")
	public ResponseEntity<?> ResetPassword(@PathVariable String email) {
		try {
			Accounts isExist = accountsService.accountsFindByEmail(email).orElse(null);
			if (isExist != null) {
				isExist.setPassword(passwordEncoder.encode("UserCube@123"));
				accountsService.accountsSave(isExist);
			} else {
				return ResponseEntity.badRequest().body(Map.of("message", "Email khong ton tai"));
			}
			return ResponseEntity.ok("Mật khẩu của tài khoản " + email + "đã được reset");

		} catch (Exception e) {
			return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/Accounts/me")
	public ResponseEntity<?> getCurrentAccount() {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts acc = accountsService.accountsFindByEmail(email)
					.orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

			AccountProfileDTO dto = new AccountProfileDTO(acc.getFullname(), acc.getEmail(), acc.getPhone(),
					acc.getAvatarUrl(), acc.getAverageOrderValue(), acc.getUserRank(), acc.getTotalSpent(),
					acc.getTotalOrder(), acc.getLoyaltyPoint(),acc.isGender(), acc.getBirthday(), acc.getCreatedAt(),acc.getUpdatedAt());

			return ResponseEntity.ok(dto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("message", "Không xác thực được người dùng"));
		}
	}

	@PutMapping("/Accounts/me/avatar")
	public ResponseEntity<?> updateAvatar(@RequestParam("file") MultipartFile file) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts acc = accountsService.accountsFindByEmail(email)
					.orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

			String url = accountsService.uploadAvatarAndGetUrl(file); // xử lý upload ảnh
			acc.setAvatarUrl(url);
			accountsService.accountsSave(acc);

			return ResponseEntity.ok(Map.of("message", "Cập nhật ảnh đại diện thành công", "avatarUrl", url));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(Map.of("message", "Lỗi khi cập nhật ảnh đại diện"));
		}
	}

	@PutMapping("/Accounts/me")
	public ResponseEntity<?> updateMyProfile(@RequestBody UpdateProfileDTO dto) {
		try {
			Accounts acc= accountsService.updateProfile(dto);
			return ResponseEntity.ok(acc);
		} catch (RuntimeException e) {
			return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(Map.of("message", "Lỗi hệ thống"));
		}
	}

	@PostMapping("/forgot-password/request")
	public ResponseEntity<?> resetPassword(@RequestBody ForgotPasswordDTO dto) {
		String email = normalizeEmail(dto.getEmail());

		int otp = otpService.generateOtpToResetPassword(5 * 60 * 1000L, email);

		emailService.sendForgotPasswordOtp(email, String.valueOf(otp));

		VerifyOtpDTO otpDto = new VerifyOtpDTO(email, String.valueOf(otp), LocalDateTime.now());
		OtpStore.putOtp(email, otpDto);

		return ResponseEntity.ok("Đã gửi mã OTP đến email.");
	}

	@PostMapping("/forgot-password/verify")
	public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpDTO dto) {
		String email = normalizeEmail(dto.getEmail());

		boolean isValid = otpService.checkOtpToResetPassword(email, dto.getOtpCode());
		if (!isValid) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã OTP không hợp lệ hoặc đã hết hạn.");
		}

		return ResponseEntity.ok("Xác minh OTP thành công.");
	}

	// 🔧 Helper để chuẩn hóa email
	private String normalizeEmail(String email) {
		return email.trim().toLowerCase();
	}

	@PostMapping("/forgot-password/change")
	public ResponseEntity<?> changePassword(@RequestBody ResetPasswordDTO dto) {
		String email = dto.getEmail().trim().toLowerCase();
		String rawPassword = dto.getNewPassword();

		if (!OtpStore.hasOtp(email)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không thể đổi mật khẩu. OTP chưa được xác minh.");
		}

		// ✅ Mã hóa tại controller
		String encodedPassword = passwordEncoder.encode(rawPassword);

		// ✅ Gửi vào service để lưu
		accountsService.resetPasswordByEmail(email, encodedPassword);

		OtpStore.clearOtp(email);

		return ResponseEntity.ok("Đặt lại mật khẩu thành công.");
	}
//	?

}