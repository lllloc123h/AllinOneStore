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
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PurchaseOrderItemsAPI {
	@Autowired
	private PurchaseOrderItemsService purchaseOrderItemsService;
	
	@Autowired
	private PurchaseOrderItemsMapper purchaseOrderItemsMapper;

	@GetMapping("/admin/PurchaseOrderItems")
	public ResponseEntity<List<PurchaseOrderItemsDTOS>> getAllPurchaseOrderItemsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<PurchaseOrderItemsDTOS> purchaseOrderItems = new ArrayList<PurchaseOrderItemsDTOS>();
		purchaseOrderItemsService.purchaseOrderItemsFindAll(page, size).forEach(e -> {
			purchaseOrderItems.add(purchaseOrderItemsMapper.mapper(e));
		});
		return ResponseEntity.ok(purchaseOrderItems);
	}

	@GetMapping("/admin/PurchaseOrderItems/{id}")
	public ResponseEntity<PurchaseOrderItems> getPurchaseOrderItemsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		PurchaseOrderItems purchaseOrderItems =(PurchaseOrderItems)purchaseOrderItemsService.purchaseOrderItemsFindById(id).orElse(new PurchaseOrderItems());
		return ResponseEntity.ok(purchaseOrderItems);
	}
	@PostMapping("/admin/PurchaseOrderItems")
	public ResponseEntity<PurchaseOrderItems> addNewPurchaseOrderItems(@RequestBody PurchaseOrderItemsDTOS entity) {
	    
	    PurchaseOrderItems saved = purchaseOrderItemsService.purchaseOrderItemsSave(purchaseOrderItemsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/PurchaseOrderItems")
	public ResponseEntity<?> updatePurchaseOrderItems(@RequestBody PurchaseOrderItems entity) {
			try {
			PurchaseOrderItems  isExist = purchaseOrderItemsService.purchaseOrderItemsFindById(id).orElse(null);
			if (isExist != null) {
				PurchaseOrderItems  update = purchaseOrderItemsMapper.mapperToObject(entity);
				purchaseOrderItemsService.purchaseOrderItemsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/PurchaseOrderItems/{id}")
	public ResponseEntity<Void> deletePurchaseOrderItems(@PathVariable int id) {
	    purchaseOrderItemsService.purchaseOrderItemsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
