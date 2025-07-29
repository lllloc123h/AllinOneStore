package com.aos.AOSBE.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.aos.AOSBE.DTOS.ChangePasswordDTOS;
import com.aos.AOSBE.DTOS.RegisterRequestDTO;
import com.aos.AOSBE.DTOS.UpdateProfileDTO;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Repository.AccountsRepository;
import com.aos.AOSBE.Repository.AuthoritiesRepository;
import com.aos.AOSBE.Repository.RolesRepository;
import com.aos.AOSBE.Repository.UserAddressesRepository;

@Service
public class AccountsService {
	@Autowired
	private AccountsRepository accountsRepository;
	@Autowired
	private AuthoritiesRepository authorityRepository;
	@Autowired
	private RolesRepository rolesRepository;
	@Autowired
	private UserAddressesRepository addressRepository;
	@Autowired
	private GenericSpecificationBuilder specBuilder;

	public Page<Accounts> accountsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Accounts> spec = specBuilder.buildFilter(filters);
		return accountsRepository.findAll(spec, pageable);
	}

	public List<Accounts> accountsFindWithFilter(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Accounts> spec = specBuilder.buildFilter(filters);
		return accountsRepository.findAll(spec, pageable).getContent();
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

	public Accounts accountsFindByEmailToCatchEvent(String email) {
		if (email == null || email.trim().isEmpty()) {
			return accountsRepository.findAccountToCountEvents("AdminToCountUserCatchEvent").orElse(null);
		}
		return accountsRepository.findByEmail(email).orElse(null);
	}

	@Transactional
	public Accounts registerByEmail(RegisterRequestDTO registerRequestDTO) {
		System.out.println("Registering user with email: " + registerRequestDTO);
		Accounts accounts = new Accounts();
		accounts.setEmail(registerRequestDTO.getEmail());
		accounts.setUserRank("Đồng");
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

		account.setPassword(new BCryptPasswordEncoder().encode(dto.getNewPassword()));
		accountsRepository.save(account);
	}

	@Transactional
	public void updateProfile(UpdateProfileDTO dto) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		Accounts account = accountsRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

		account.setFullname(dto.getFullname());
		account.setEmail(dto.getEmail());
		account.setPhone(dto.getPhone());

		accountsRepository.save(account);
	}

	@Transactional
	public String uploadAvatarAndGetUrl(MultipartFile file) {
		try {

			String uploadDir = "D:/AllinOneStore/uploads/avatar/";
			Files.createDirectories(Paths.get(uploadDir));

			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
			Path filePath = Paths.get(uploadDir + filename);
			Files.write(filePath, file.getBytes());

			String avatarUrl = "/avatar/" + filename;

			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts account = accountsRepository.findByEmail(email)
					.orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
			account.setAvatarUrl(avatarUrl);
			accountsRepository.save(account);

			return avatarUrl;
		} catch (IOException e) {
			throw new RuntimeException("Không thể lưu ảnh đại diện", e);
		}
	}

	public void resetPasswordByEmail(String email, String encodedPassword) {
		Accounts account = accountsRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Email không tồn tại: " + email));

		account.setPassword(encodedPassword);
		accountsRepository.save(account);
	}

}
