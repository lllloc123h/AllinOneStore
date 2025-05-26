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
import org.springframework.data.domain.PageRequest;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdersAPI {
	@Autowired
	private OrdersService ordersService;

	@GetMapping("/Orders")
	public ResponseEntity<List<Orders>> getAllOrdersApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<Orders> orders = ordersService.ordersFindAll(page,size);
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/Orders/{id}")
	public ResponseEntity<Orders> getOrdersByIdApi(@PathVariable int id) {
		Orders orders =(Orders)ordersService.ordersFindById(id).orElse(new Orders());
		return ResponseEntity.ok(orders);
	}
	@PostMapping("/Orders")
	public ResponseEntity<Orders> addNewOrders(@RequestBody Orders entity) {
	    Orders saved = ordersService.ordersSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Orders")
	public ResponseEntity<Orders> updateOrders(@RequestBody Orders entity) {
	    Orders updated = ordersService.ordersSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Orders/{id}")
	public ResponseEntity<Void> deleteOrders(@PathVariable int id) {
	    ordersService.ordersDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
