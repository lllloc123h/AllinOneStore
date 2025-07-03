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

import com.aos.AOSBE.DTOS.PurchaseOrderItemsDTOS;
import com.aos.AOSBE.Entity.PurchaseOrderItems;
import com.aos.AOSBE.Mapper.PurchaseOrderItemsMapper;
import com.aos.AOSBE.Service.PurchaseOrderItemsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PurchaseOrderItemsAPI {
	@Autowired
	private PurchaseOrderItemsService purchaseOrderItemsService;

	@Autowired
	private PurchaseOrderItemsMapper purchaseOrderItemsMapper;

	@GetMapping("/admin/PurchaseOrderItems")
	public ResponseEntity<?> getAllPurchaseOrderItemsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<PurchaseOrderItems> pageResult = purchaseOrderItemsService.purchaseOrderItemsFindAll(page, size, filters);
		List<PurchaseOrderItemsDTOS> purchaseOrderItems = pageResult.getContent().stream()
				.map(purchaseOrderItemsMapper::mapper).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", purchaseOrderItems);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/PurchaseOrderItems/{id}")
	public ResponseEntity<PurchaseOrderItems> getPurchaseOrderItemsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		PurchaseOrderItems purchaseOrderItems = (PurchaseOrderItems) purchaseOrderItemsService
				.purchaseOrderItemsFindById(id).orElse(new PurchaseOrderItems());
		return ResponseEntity.ok(purchaseOrderItems);
	}

	@PostMapping("/admin/PurchaseOrderItems")
	public ResponseEntity<PurchaseOrderItems> addNewPurchaseOrderItems(@RequestBody PurchaseOrderItemsDTOS entity) {

		PurchaseOrderItems saved = purchaseOrderItemsService
				.purchaseOrderItemsSave(purchaseOrderItemsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/PurchaseOrderItems/{id}")
	public ResponseEntity<?> updatePurchaseOrderItems(@PathVariable int id,
			@RequestBody PurchaseOrderItemsDTOS entity) {
		try {
			PurchaseOrderItems isExist = purchaseOrderItemsService.purchaseOrderItemsFindById(id).orElse(null);
			if (isExist != null) {
				PurchaseOrderItems update = purchaseOrderItemsMapper.mapperToObject(entity);
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