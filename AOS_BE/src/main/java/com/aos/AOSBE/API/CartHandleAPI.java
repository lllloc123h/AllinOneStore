package com.aos.AOSBE.API;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import com.aos.AOSBE.DTOS.CreateComboDTO;
import com.aos.AOSBE.DTOS.CustomsDTOS;
import com.aos.AOSBE.DTOS.UpdateComboDTO;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Customs;
import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Mapper.CustomsMapper;
import com.aos.AOSBE.Service.*;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.DTOS.CartItemsDTOS;
import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Mapper.CartItemsMapper;

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
	@Autowired
	PromotionProductsService promotionProductsService;
	@Autowired
	PromotionsService promotionsService;
	@Autowired
	private CustomsService customsService;
	@Autowired
	private CustomsMapper customsMapper;

	@PostMapping("/addToCart")
	public ResponseEntity<?> addToCart(@RequestBody CartItemsDTOS entity) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			CartItems cartItem = cartItemsMapper.mapperToObject(entity);
			CartItems item = cartItemsService.cartFindByAccountEmailAndProductItemId(
					userEmail,
					cartItem.getProductItems().getId());
			if (item != null && item.getComboGroupId() == null) {
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
	public ResponseEntity<?> updateCart(@RequestBody CartItemsDTOS entity,
										@RequestParam("updateType") String updateType) {
		try {
			// Find the existing cart item by ID
			CartItems existingItem = cartItemsService.cartItemsFindById(entity.getId()).orElse(null);
			if (existingItem != null) {
				int currentQty = entity.getQty();
				switch (updateType) {
					case "increase":
						existingItem.setQty(currentQty + 1);
						cartItemsService.cartItemsSave(existingItem);
						break;
					case "decrease":
						if (currentQty - 1 <= 0) {
							cartItemsService.cartItemsDeleteById(entity.getId());
							return ResponseEntity.ok(Map.of("message", "Item removed from cart"));
						} else {
							existingItem.setQty(currentQty - 1);
							cartItemsService.cartItemsSave(existingItem);
						}
						break;
					default:
						return ResponseEntity.badRequest().body(Map.of("message", "Invalid update type"));
				}
				return ResponseEntity.ok(existingItem);
			} else {
				return ResponseEntity.status(404).body(Map.of("message", "Cart item not found"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("message", "An error occurred"));
		}
	}

	@GetMapping("/cart")
	public ResponseEntity<?> cart() {
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		List<CartItemsDTOS> cartListByAcount = cartItemsService.cartItemsFindAccounts(userEmail).stream().map(cartItemsMapper::mapper).toList();
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

	@PostMapping("/cart/addCombo")
	public ResponseEntity<?> addCombo(@RequestBody CreateComboDTO entity) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts account = accountsService.accountsFindByEmail(userEmail).orElse(null);
			System.err.println(entity);
			cartItemsService.addCombo(entity, account);
			return ResponseEntity.ok(Map.of("message", "Combo added successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@PutMapping("/cart/updateComboQty")
	public ResponseEntity<?> updateComboQty(@RequestBody UpdateComboDTO entity) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts account = accountsService.accountsFindByEmail(userEmail).orElse(null);
			List<CartItems> listCartItems = cartItemsService.findCartItemsByAccountsAndComboGroupId(account, entity.getComboGroupId());
			for (CartItems cartItems : listCartItems) {
				int tempQty = cartItems.getQty() / cartItems.getComboQty();
				if (Objects.equals(entity.getType(), "decrease")){
					cartItems.setQty(cartItems.getQty() - tempQty);
				}else{
					cartItems.setQty(cartItems.getQty() + tempQty);

				}
				cartItems.setComboQty(entity.getComboQty());
				cartItemsService.cartItemsSave(cartItems);
			}
			return ResponseEntity.ok(Map.of("message", "Combo quantity updated successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/cart/deleteCombo/{comboGroupId}")
	public ResponseEntity<?> deleteCombo(@PathVariable("comboGroupId") UUID comboGroupId) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts account = accountsService.accountsFindByEmail(userEmail).orElse(null);
	cartItemsService.deleteCombo(account, comboGroupId);
			return ResponseEntity.ok(Map.of("message", "Combo deleted successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}
	@GetMapping("/customs/email")
	public ResponseEntity<List<CustomsDTOS>> getCustomsByEmail() {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		System.out.println(">> Email: " + email);
		List<Customs> customsList = customsService.findCustomsByEmail(email);
		List<CustomsDTOS> customsDTOSList = customsList.stream()
				.map(customsMapper::mapper)
				.collect(Collectors.toList());
		System.out.println(">> Customs List: " + customsDTOSList);
		return ResponseEntity.ok(customsDTOSList);
	}
	@PostMapping("/customs/save")
	public ResponseEntity<?> addCustoms(@RequestBody CustomsDTOS entity) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts account = accountsService.accountsFindByEmail(userEmail).orElse(null);
			customsService.customsSave(customsMapper.mapperToObject(entity));
			return ResponseEntity.ok(Map.of("message", "Customs added successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}
}
