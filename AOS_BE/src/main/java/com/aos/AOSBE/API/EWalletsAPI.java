package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.aos.AOSBE.CommonFunctions.CommonFunctions;
import com.aos.AOSBE.DTOS.EWalletsDTOS;
import com.aos.AOSBE.DTOS.VerifyEWalletsDTOS;
import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Mapper.EWalletsMapper;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.EWalletsService;
import com.aos.AOSBE.Service.EmailService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class EWalletsAPI {

	@Autowired
	private EWalletsService eWalletsService;

	@Autowired
	AccountsService accountsService;

	@Autowired
	private EWalletsMapper eWalletsMapper;

	@Autowired
	private EmailService emailService;

	@GetMapping("/admin/EWallets")
	public ResponseEntity<?> getAllEWalletsApiAdminRole(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<EWallets> pageResult = eWalletsService.eWalletsFindAll(page, size, filters);
		List<EWalletsDTOS> eWallets = pageResult.getContent().stream().map(eWalletsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", eWallets);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/EWallets")
	public ResponseEntity<?> getAllEWalletsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			EWallets eWallets = eWalletsService.eWalletsFindByAccountEmail(userEmail).orElse(null);
			if (eWallets != null) {
				return ResponseEntity.ok(eWallets);
			} else {
				return ResponseEntity.badRequest().body(Map.of("message", "404"));
			}
		} catch (Exception c) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@GetMapping("/admin/EWallets/{id}")
	public ResponseEntity<EWallets> getEWalletsByIdApi(@PathVariable String id) {
		// try{
		// }catch(Exception e){
		// }
		EWallets eWallets = (EWallets) eWalletsService.eWalletsFindById(id).orElse(null);
		return ResponseEntity.ok(eWallets);
	}

	@PostMapping("/admin/EWallets")
	public ResponseEntity<EWallets> addNewEWallets(@RequestBody EWalletsDTOS entity) {
		EWallets saved = eWalletsService.eWalletsSave(eWalletsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PostMapping("/user/EWallets")
	public ResponseEntity<?> addNewUserEWallets(@RequestBody EWalletsDTOS entity) {
		try {
			CommonFunctions commonFunctions = new CommonFunctions();
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			String CodeActice = commonFunctions.generateVerificationCode();
			entity.setAccounts(userEmail);
			entity.setActive(false);
			entity.setBalance(0);
			entity.setCodeActivce(CodeActice);
			entity.setWalletType("REAL");
			EWallets saved = eWalletsService.eWalletsSave(eWalletsMapper.mapperToObject(entity));
			emailService.sendVerificationEWallet("nkha79323@gmail.com", CodeActice);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra:" + e.getMessage()));
		}
	}

	@PostMapping("/user/VerifyEWallets")
	public ResponseEntity<?> verifyNewUserEWallets(@RequestBody VerifyEWalletsDTOS entity) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			EWallets ewallet = eWalletsService.eWalletsFindByAccountEmail(userEmail).orElse(null);
			if (ewallet != null && ewallet.isActive() == false
					&& entity.getCodeActivce().equals(ewallet.getCodeActivce())) {
				ewallet.setActive(true);
				EWallets saved = eWalletsService.eWalletsSave(ewallet);
				return ResponseEntity.ok(saved);
			} else {
				return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra: Không tìm thấy Ewallet"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra:" + e.getMessage()));
		}
	}

	@PutMapping("/admin/EWallets/{id}")
	public ResponseEntity<?> updateEWallets(@PathVariable String id, @RequestBody EWalletsDTOS entity) {
		try {
			EWallets isExist = eWalletsService.eWalletsFindById(id).orElse(null);
			if (isExist != null) {
				EWallets update = eWalletsMapper.mapperToObject(entity);
				eWalletsService.eWalletsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/EWallets/{id}")
	public ResponseEntity<Void> deleteEWallets(@PathVariable String id) {
		eWalletsService.eWalletsDeleteById(id);
		return ResponseEntity.noContent().build();
	}
}
