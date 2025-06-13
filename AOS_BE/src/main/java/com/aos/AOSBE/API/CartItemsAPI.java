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
public class CartItemsAPI {
	@Autowired
	private CartItemsService cartItemsService;
	
	@Autowired
	private CartItemsMapper cartItemsMapper;

	@GetMapping("/admin/CartItems")
	public ResponseEntity<List<CartItemsDTOS>> getAllCartItemsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CartItemsDTOS> cartItems = new ArrayList<CartItemsDTOS>();
		cartItemsService.cartItemsFindAll(page, size).forEach(e -> {
			cartItems.add(cartItemsMapper.mapper(e));
		});
		return ResponseEntity.ok(cartItems);
	}

	@GetMapping("/admin/CartItems/{id}")
	public ResponseEntity<CartItems> getCartItemsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		CartItems cartItems =(CartItems)cartItemsService.cartItemsFindById(id).orElse(new CartItems());
		return ResponseEntity.ok(cartItems);
	}
	@PostMapping("/admin/CartItems")
	public ResponseEntity<CartItems> addNewCartItems(@RequestBody CartItemsDTOS entity) {
	    
	    CartItems saved = cartItemsService.cartItemsSave(cartItemsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/CartItems")
	public ResponseEntity<?> updateCartItems(@RequestBody CartItems entity) {
			try {
			CartItems  isExist = cartItemsService.cartItemsFindById(id).orElse(null);
			if (isExist != null) {
				CartItems  update = cartItemsMapper.mapperToObject(entity);
				cartItemsService.cartItemsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/CartItems/{id}")
	public ResponseEntity<Void> deleteCartItems(@PathVariable int id) {
	    cartItemsService.cartItemsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
