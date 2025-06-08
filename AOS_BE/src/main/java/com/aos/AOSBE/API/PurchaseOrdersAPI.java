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
public class PurchaseOrdersAPI {
	@Autowired
	private PurchaseOrdersService purchaseOrdersService;
	
	@Autowired
	private PurchaseOrdersMapper purchaseOrdersMapper;

	@GetMapping("/PurchaseOrders")
	public ResponseEntity<List<PurchaseOrdersDTOS>> getAllPurchaseOrdersApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<PurchaseOrdersDTOS> purchaseOrders = new ArrayList<PurchaseOrdersDTOS>();
		purchaseOrdersService.purchaseOrdersFindAll(page, size).forEach(e -> {
			purchaseOrders.add(purchaseOrdersMapper.mapper(e));
		});
		return ResponseEntity.ok(purchaseOrders);
	}

	@GetMapping("/PurchaseOrders/{id}")
	public ResponseEntity<PurchaseOrders> getPurchaseOrdersByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		PurchaseOrders purchaseOrders =(PurchaseOrders)purchaseOrdersService.purchaseOrdersFindById(id).orElse(new PurchaseOrders());
		return ResponseEntity.ok(purchaseOrders);
	}
	@PostMapping("/PurchaseOrders")
	public ResponseEntity<PurchaseOrders> addNewPurchaseOrders(@RequestBody PurchaseOrdersDTOS entity) {
	    
	    PurchaseOrders saved = purchaseOrdersService.purchaseOrdersSave(purchaseOrdersMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/PurchaseOrders")
	public ResponseEntity<PurchaseOrders> updatePurchaseOrders(@RequestBody PurchaseOrders entity) {
	    PurchaseOrders updated = purchaseOrdersService.purchaseOrdersSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/PurchaseOrders/{id}")
	public ResponseEntity<Void> deletePurchaseOrders(@PathVariable int id) {
	    purchaseOrdersService.purchaseOrdersDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
