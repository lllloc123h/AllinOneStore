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

import com.aos.AOSBE.DTOS.PaymentMethodsDTOS;
import com.aos.AOSBE.Entity.PaymentMethods;
import com.aos.AOSBE.Mapper.PaymentMethodsMapper;
import com.aos.AOSBE.Service.PaymentMethodsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentMethodsAPI {
	@Autowired
	private PaymentMethodsService paymentMethodsService;

	@Autowired
	private PaymentMethodsMapper paymentMethodsMapper;

	@GetMapping("/admin/PaymentMethods")
	public ResponseEntity<?> getAllPaymentMethodsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<PaymentMethods> pageResult = paymentMethodsService.paymentMethodsFindAll(page, size, filters);
		List<PaymentMethodsDTOS> paymentMethods = pageResult.getContent().stream().map(paymentMethodsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", paymentMethods);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/PaymentMethods/{id}")
	public ResponseEntity<PaymentMethods> getPaymentMethodsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		PaymentMethods paymentMethods = (PaymentMethods) paymentMethodsService.paymentMethodsFindById(id)
				.orElse(new PaymentMethods());
		return ResponseEntity.ok(paymentMethods);
	}

	@PostMapping("/admin/PaymentMethods")
	public ResponseEntity<PaymentMethods> addNewPaymentMethods(@RequestBody PaymentMethodsDTOS entity) {

		PaymentMethods saved = paymentMethodsService.paymentMethodsSave(paymentMethodsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/PaymentMethods/{id}")
	public ResponseEntity<?> updatePaymentMethods(@PathVariable int id, @RequestBody PaymentMethodsDTOS entity) {
		try {
			PaymentMethods isExist = paymentMethodsService.paymentMethodsFindById(id).orElse(null);
			if (isExist != null) {
				PaymentMethods update = paymentMethodsMapper.mapperToObject(entity);
				paymentMethodsService.paymentMethodsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/PaymentMethods/{id}")
	public ResponseEntity<Void> deletePaymentMethods(@PathVariable int id) {
		paymentMethodsService.paymentMethodsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}