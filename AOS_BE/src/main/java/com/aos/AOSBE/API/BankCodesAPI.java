package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.client.RestTemplate;

import com.aos.AOSBE.DTOS.BankCodesDTOS;
import com.aos.AOSBE.Entity.BankCodes;
import com.aos.AOSBE.Mapper.BankCodesMapper;
import com.aos.AOSBE.Service.BankCodesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class BankCodesAPI {
	@Autowired
	private BankCodesService bankCodesService;

	@Autowired
	private BankCodesMapper bankCodesMapper;

	@GetMapping("/admin/BankCodes")
	public ResponseEntity<?> getAllBankCodesApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<BankCodes> pageResult = bankCodesService.bankCodesFindAll(page, size, filters);
		List<BankCodesDTOS> bankCodes = pageResult.getContent().stream().map(bankCodesMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", bankCodes);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/user/BankCodes")
	public ResponseEntity<?> getAllBankCodesApiUserRoles(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		String endpoint = "https://payment.momo.vn/v2/gateway/api/bankcodes";
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://payment.momo.vn/v2/gateway/api/bankcodes";

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		return ResponseEntity.ok(response.getBody());
	}

	@GetMapping("/admin/BankCodes/{id}")
	public ResponseEntity<BankCodes> getBankCodesByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		BankCodes bankCodes = (BankCodes) bankCodesService.bankCodesFindById(id).orElse(new BankCodes());
		return ResponseEntity.ok(bankCodes);
	}

	@PostMapping("/admin/BankCodes")
	public ResponseEntity<BankCodes> addNewBankCodes(@RequestBody BankCodesDTOS entity) {

		BankCodes saved = bankCodesService.bankCodesSave(bankCodesMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/BankCodes/{id}")
	public ResponseEntity<?> updateBankCodes(@PathVariable int id, @RequestBody BankCodesDTOS entity) {
		try {
			BankCodes isExist = bankCodesService.bankCodesFindById(id).orElse(null);
			if (isExist != null) {
				BankCodes update = bankCodesMapper.mapperToObject(entity);
				bankCodesService.bankCodesSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/BankCodes/{id}")
	public ResponseEntity<Void> deleteBankCodes(@PathVariable int id) {
		bankCodesService.bankCodesDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
