package com.aos.AOSBE.API;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentMethodsAPI {
	@Autowired
	private PaymentMethodsService paymentMethodsService;

	@GetMapping("/PaymentMethods")
	public ResponseEntity<List<PaymentMethods>> getAllPaymentMethodsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<PaymentMethods> paymentMethods = paymentMethodsService.paymentMethodsFindAll();
		return ResponseEntity.ok(paymentMethods);
	}

	@GetMapping("/PaymentMethods/{id}")
	public ResponseEntity<PaymentMethods> getPaymentMethodsByIdApi(@PathVariable int id) {
		PaymentMethods paymentMethods =(PaymentMethods)paymentMethodsService.paymentMethodsFindById(id).orElse(new PaymentMethods());
		return ResponseEntity.ok(paymentMethods);
	}
	@PostMapping("/PaymentMethods")
	public ResponseEntity<PaymentMethods> addNewPaymentMethods(@RequestBody PaymentMethods entity) {
	    PaymentMethods saved = paymentMethodsService.paymentMethodsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/PaymentMethods")
	public ResponseEntity<PaymentMethods> updatePaymentMethods(@RequestBody PaymentMethods entity) {
	    PaymentMethods updated = paymentMethodsService.paymentMethodsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/PaymentMethods/{id}")
	public ResponseEntity<Void> deletePaymentMethods(@PathVariable int id) {
	    paymentMethodsService.paymentMethodsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
