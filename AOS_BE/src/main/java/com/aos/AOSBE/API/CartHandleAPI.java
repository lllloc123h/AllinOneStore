package com.aos.AOSBE.API;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.DTOS.CartItemsDTOS;
import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Mapper.CartItemsMapper;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.CartItemsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CartHandleAPI {
	@Autowired
	CartItemsService cartItemsService;
	@Autowired
	CartItemsMapper cartItemsMapper;
	@Autowired
	AccountsService accountsService;

	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCart(@RequestBody CartItemsDTOS entity) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			CartItems cartItem = cartItemsMapper.mapperToObject(entity);
			CartItems item = cartItemsService.cartFindByAccountEmailAndProductItemId(userEmail,
					cartItem.getProductItems().getId());
			if (item != null) {
				item.setQty(item.getQty() + cartItem.getQty());
				cartItemsService.cartItemsSave(item);
				return ResponseEntity.ok(item);
			} else {
				cartItemsService.cartItemsSave(cartItem);
				return ResponseEntity.ok(cartItem);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@PutMapping("/addToCart")
	public ResponseEntity<?> updateCart(@RequestBody CartItemsDTOS entity) {
		try {
//			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
//			CartItems item = cartItemsService.cartFindByAccountEmailAndProductItemId(userEmail,
//					cartItem.getProductItems().getId());
			CartItems itemIsExist = cartItemsService.cartItemsFindById(entity.getId()).orElse(null);
			if (itemIsExist != null) {
				itemIsExist.setQty(entity.getQty());
				cartItemsService.cartItemsSave(itemIsExist);
			}
			return ResponseEntity.ok(itemIsExist);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@GetMapping("/cart")
	public ResponseEntity<List<CartItems>> cart() {
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		List<CartItems> cartListByAcount = cartItemsService.cartItemsFindAccounts(userEmail);

		return ResponseEntity.ok(cartListByAcount);
	}

	@DeleteMapping("/cart/{id}")
	public ResponseEntity<?> deleteCart(@PathVariable int id) {
		try {
			CartItems itemIsExist = cartItemsService.cartItemsFindById(id).orElse(null);
			if (itemIsExist != null) {
				cartItemsService.cartItemsDeleteById(id);
			}
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

}
