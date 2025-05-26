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
public class PurchaseOrdersAPI {
	@Autowired
	private PurchaseOrdersService purchaseOrdersService;

	@GetMapping("/PurchaseOrders")
	public ResponseEntity<List<PurchaseOrders>> getAllPurchaseOrdersApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<PurchaseOrders> purchaseOrders = purchaseOrdersService.purchaseOrdersFindAll();
		return ResponseEntity.ok(purchaseOrders);
	}

	@GetMapping("/PurchaseOrders/{id}")
	public ResponseEntity<PurchaseOrders> getPurchaseOrdersByIdApi(@PathVariable int id) {
		PurchaseOrders purchaseOrders =(PurchaseOrders)purchaseOrdersService.purchaseOrdersFindById(id).orElse(new PurchaseOrders());
		return ResponseEntity.ok(purchaseOrders);
	}
	@PostMapping("/PurchaseOrders")
	public ResponseEntity<PurchaseOrders> addNewPurchaseOrders(@RequestBody PurchaseOrders entity) {
	    PurchaseOrders saved = purchaseOrdersService.purchaseOrdersSave(entity);
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
