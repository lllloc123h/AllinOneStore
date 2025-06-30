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

import com.aos.AOSBE.DTOS.EWalletTransactionsDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.EWalletTransactions;
import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Mapper.EWalletTransactionsMapper;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.EWalletTransactionsService;
import com.aos.AOSBE.Service.EWalletsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class EWalletTransactionsAPI {
	@Autowired
	private EWalletTransactionsService eWalletTransactionsService;

	@Autowired
	private EWalletTransactionsMapper eWalletTransactionsMapper;
	@Autowired
	AccountsService accountsService;
	@Autowired
	private EWalletsService eWalletsService;

	@GetMapping("/admin/EWalletTransactions")
	public ResponseEntity<List<EWalletTransactionsDTOS>> getAllEWalletTransactionsApiAdminRole(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");

		List<EWalletTransactionsDTOS> eWalletTransactions = new ArrayList<EWalletTransactionsDTOS>();
		eWalletTransactionsService.eWalletTransactionsFindAll(page, size, filters).forEach(e -> {
			eWalletTransactions.add(eWalletTransactionsMapper.mapper(e));
		});
		return ResponseEntity.ok(eWalletTransactions);
	}

	@GetMapping("/EWalletTransactions")
	public ResponseEntity<?> getAllEWalletTransactionsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts user = accountsService.accountsFindByEmail(userEmail).orElse(null);
			EWallets eWallets = eWalletsService.eWalletsFindByAccountId(user.getId()).orElse(null);
			List<EWalletTransactionsDTOS> eWalletTransactions = new ArrayList<EWalletTransactionsDTOS>();
			eWalletTransactionsService.eWalletTransactionsFindByEwalletId(eWallets.getId()).forEach(e -> {
				eWalletTransactions.add(eWalletTransactionsMapper.mapper(e));
			});
			return ResponseEntity.ok(eWalletTransactions);
		} catch (Exception e2) {
			return ResponseEntity.badRequest().body(Map.of("message", "đã có lỗi xảy ra"));
		}
	}

	@GetMapping("/admin/EWalletTransactions/{id}")
	public ResponseEntity<EWalletTransactions> getEWalletTransactionsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		EWalletTransactions eWalletTransactions = (EWalletTransactions) eWalletTransactionsService
				.eWalletTransactionsFindById(id).orElse(new EWalletTransactions());
		return ResponseEntity.ok(eWalletTransactions);
	}

	@PostMapping("/admin/EWalletTransactions")
	public ResponseEntity<EWalletTransactions> addNewEWalletTransactions(@RequestBody EWalletTransactionsDTOS entity) {

		EWalletTransactions saved = eWalletTransactionsService
				.eWalletTransactionsSave(eWalletTransactionsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/EWalletTransactions/{id}")
	public ResponseEntity<?> updateEWalletTransactions(@PathVariable int id,
			@RequestBody EWalletTransactionsDTOS entity) {
		try {
			EWalletTransactions isExist = eWalletTransactionsService.eWalletTransactionsFindById(id).orElse(null);
			if (isExist != null) {
				EWalletTransactions update = eWalletTransactionsMapper.mapperToObject(entity);
				eWalletTransactionsService.eWalletTransactionsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/EWalletTransactions/{id}")
	public ResponseEntity<Void> deleteEWalletTransactions(@PathVariable int id) {
		eWalletTransactionsService.eWalletTransactionsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}