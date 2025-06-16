package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PurchaseOrdersAPI {
	@Autowired
	private PurchaseOrdersService purchaseOrdersService;
	
	@Autowired
	private PurchaseOrdersMapper purchaseOrdersMapper;

	@GetMapping("/admin/PurchaseOrders")
	public ResponseEntity<List<PurchaseOrdersDTOS>> getAllPurchaseOrdersApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<PurchaseOrdersDTOS> purchaseOrders = new ArrayList<PurchaseOrdersDTOS>();
		purchaseOrdersService.purchaseOrdersFindAll(page, size).forEach(e -> {
			purchaseOrders.add(purchaseOrdersMapper.mapper(e));
		});
		return ResponseEntity.ok(purchaseOrders);
	}

	@GetMapping("/admin/PurchaseOrders/{id}")
	public ResponseEntity<PurchaseOrders> getPurchaseOrdersByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		PurchaseOrders purchaseOrders =(PurchaseOrders)purchaseOrdersService.purchaseOrdersFindById(id).orElse(new PurchaseOrders());
		return ResponseEntity.ok(purchaseOrders);
	}
	@PostMapping("/admin/PurchaseOrders")
	public ResponseEntity<PurchaseOrders> addNewPurchaseOrders(@RequestBody PurchaseOrdersDTOS entity) {
	    
	    PurchaseOrders saved = purchaseOrdersService.purchaseOrdersSave(purchaseOrdersMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/PurchaseOrders/{id}")
	public ResponseEntity<?> updatePurchaseOrders( @PathVariable int id,@RequestBody PurchaseOrdersDTOS entity) {
			try {
			PurchaseOrders  isExist = purchaseOrdersService.purchaseOrdersFindById(id).orElse(null);
			if (isExist != null) {
				PurchaseOrders  update = purchaseOrdersMapper.mapperToObject(entity);
				purchaseOrdersService.purchaseOrdersSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/PurchaseOrders/{id}")
	public ResponseEntity<Void> deletePurchaseOrders(@PathVariable int id) {
	    purchaseOrdersService.purchaseOrdersDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
