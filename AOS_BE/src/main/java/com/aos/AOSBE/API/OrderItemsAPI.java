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
public class OrderItemsAPI {
	@Autowired
	private OrderItemsService orderItemsService;

	@GetMapping("/OrderItems")
	public ResponseEntity<List<OrderItems>> getAllOrderItemsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<OrderItems> orderItems = orderItemsService.orderItemsFindAll();
		return ResponseEntity.ok(orderItems);
	}

	@GetMapping("/OrderItems/{id}")
	public ResponseEntity<OrderItems> getOrderItemsByIdApi(@PathVariable int id) {
		OrderItems orderItems =(OrderItems)orderItemsService.orderItemsFindById(id).orElse(new OrderItems());
		return ResponseEntity.ok(orderItems);
	}
	@PostMapping("/OrderItems")
	public ResponseEntity<OrderItems> addNewOrderItems(@RequestBody OrderItems entity) {
	    OrderItems saved = orderItemsService.orderItemsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/OrderItems")
	public ResponseEntity<OrderItems> updateOrderItems(@RequestBody OrderItems entity) {
	    OrderItems updated = orderItemsService.orderItemsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/OrderItems/{id}")
	public ResponseEntity<Void> deleteOrderItems(@PathVariable int id) {
	    orderItemsService.orderItemsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
