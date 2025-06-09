package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Mapper.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class PaymentMethodsAPI {
	@Autowired
	private PaymentMethodsService paymentMethodsService;
	
	@Autowired
	private PaymentMethodsMapper paymentMethodsMapper;

	@GetMapping("/PaymentMethods")
	public ResponseEntity<List<PaymentMethodsDTOS>> getAllPaymentMethodsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<PaymentMethodsDTOS> paymentMethods = new ArrayList<PaymentMethodsDTOS>();
		paymentMethodsService.paymentMethodsFindAll(page, size).forEach(e -> {
			paymentMethods.add(paymentMethodsMapper.mapper(e));
		});
		return ResponseEntity.ok(paymentMethods);
	}

	@GetMapping("/PaymentMethods/{id}")
	public ResponseEntity<PaymentMethods> getPaymentMethodsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		PaymentMethods paymentMethods =(PaymentMethods)paymentMethodsService.paymentMethodsFindById(id).orElse(new PaymentMethods());
		return ResponseEntity.ok(paymentMethods);
	}
	@PostMapping("/PaymentMethods")
	public ResponseEntity<PaymentMethods> addNewPaymentMethods(@RequestBody PaymentMethodsDTOS entity) {
	    
	    PaymentMethods saved = paymentMethodsService.paymentMethodsSave(paymentMethodsMapper.mapperToObject(entity));	    
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
