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
public class CartItemsAPI {
	@Autowired
	private CartItemsService cartItemsService;
	private CartItemsMapper cartItemsMapper=new CartItemsMapper();

	@GetMapping("/CartItems")
	public ResponseEntity<List<CartItemsDTOS>> getAllCartItemsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CartItemsDTOS> cartItems = new ArrayList<CartItemsDTOS>();
		cartItemsService.cartItemsFindAll(page, size).forEach(e -> {
			cartItems.add(cartItemsMapper.mapper(e));
		});
		return ResponseEntity.ok(cartItems);
	}

	@GetMapping("/CartItems/{id}")
	public ResponseEntity<CartItems> getCartItemsByIdApi(@PathVariable int id) {
		CartItems cartItems =(CartItems)cartItemsService.cartItemsFindById(id).orElse(new CartItems());
		return ResponseEntity.ok(cartItems);
	}
	@PostMapping("/CartItems")
	public ResponseEntity<CartItems> addNewCartItems(@RequestBody CartItemsDTOS entity) {
	    
	    CartItems saved = cartItemsService.cartItemsSave(cartItemsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/CartItems")
	public ResponseEntity<CartItems> updateCartItems(@RequestBody CartItems entity) {
	    CartItems updated = cartItemsService.cartItemsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/CartItems/{id}")
	public ResponseEntity<Void> deleteCartItems(@PathVariable int id) {
	    cartItemsService.cartItemsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
