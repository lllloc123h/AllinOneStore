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

import com.aos.AOSBE.DTOS.CartItemsDTOS;
import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Mapper.CartItemsMapper;
import com.aos.AOSBE.Service.CartItemsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CartItemsAPI {
	@Autowired
	private CartItemsService cartItemsService;

	@Autowired
	private CartItemsMapper cartItemsMapper;

	@GetMapping("/admin/CartItems")
	public ResponseEntity<?> getAllCartItemsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<CartItems> pageResult = cartItemsService.cartItemsFindAll(page, size, filters);
		List<CartItemsDTOS> cartItems = pageResult.getContent().stream().map(cartItemsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", cartItems);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/CartItems/{id}")
	public ResponseEntity<CartItems> getCartItemsByIdApi(@PathVariable int id) {
		CartItems cartItems = (CartItems) cartItemsService.cartItemsFindById(id).orElse(new CartItems());
		return ResponseEntity.ok(cartItems);
	}

	@PostMapping("/admin/CartItems")
	public ResponseEntity<CartItems> addNewCartItems(@RequestBody CartItemsDTOS entity) {

		CartItems saved = cartItemsService.cartItemsSave(cartItemsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/CartItems/{id}")
	public ResponseEntity<?> updateCartItems(@PathVariable int id, @RequestBody CartItemsDTOS entity) {
		try {
			CartItems isExist = cartItemsService.cartItemsFindById(id).orElse(null);
			if (isExist != null) {
				CartItems update = cartItemsMapper.mapperToObject(entity);
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