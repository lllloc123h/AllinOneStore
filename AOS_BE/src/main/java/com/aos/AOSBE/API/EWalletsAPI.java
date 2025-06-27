package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.aos.AOSBE.DTOS.EWalletsDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Mapper.EWalletsMapper;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.EWalletsService;

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

	@GetMapping("/admin/EWallets")
	public ResponseEntity<List<EWalletsDTOS>> getAllEWalletsApiAdminRole(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<EWalletsDTOS> eWallets = new ArrayList<EWalletsDTOS>();
		eWalletsService.eWalletsFindAll(page, size).forEach(e -> {
			eWallets.add(eWalletsMapper.mapper(e));
		});
		return ResponseEntity.ok(eWallets);
	}

	@GetMapping("/EWallets")
	public ResponseEntity<?> getAllEWalletsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		try {

			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts user = accountsService.accountsFindByEmail(userEmail).orElse(null);
			EWallets eWallets = eWalletsService.eWalletsFindByAccountId(user.getId()).orElse(null);
			if (eWallets != null) {
				return ResponseEntity.ok(eWallets);
			} else {
				return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
			}
		} catch (Exception c) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@GetMapping("/admin/EWallets/{id}")
	public ResponseEntity<EWallets> getEWalletsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		EWallets eWallets = (EWallets) eWalletsService.eWalletsFindById(id);
		return ResponseEntity.ok(eWallets);
	}

	@PostMapping("/admin/EWallets")
	public ResponseEntity<EWallets> addNewEWallets(@RequestBody EWalletsDTOS entity) {

		EWallets saved = eWalletsService.eWalletsSave(eWalletsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/EWallets/{id}")
	public ResponseEntity<?> updateEWallets(@PathVariable int id, @RequestBody EWalletsDTOS entity) {
		try {
			EWallets isExist = eWalletsService.eWalletsFindById(id);
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
	public ResponseEntity<Void> deleteEWallets(@PathVariable int id) {
		eWalletsService.eWalletsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
