package com.aos.AOSBE.API;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

import com.aos.AOSBE.DTOS.CouponsDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Coupons;
import com.aos.AOSBE.Mapper.CouponsMapper;
import com.aos.AOSBE.Repository.AccountsRepository;
import com.aos.AOSBE.Repository.CouponsRepository;
import com.aos.AOSBE.Repository.OrdersRepository;
import com.aos.AOSBE.Service.CouponsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class CouponsAPI {
	@Autowired
	private CouponsService couponsService;

	@Autowired
	private CouponsMapper couponsMapper;
	
	@Autowired
	private CouponsRepository couponsRepository;
	
	@Autowired
	private AccountsRepository accountsRepository;
	
	@Autowired
	private OrdersRepository ordersRepository;

	@GetMapping("/admin/Coupons")
	public ResponseEntity<?> getAllCouponsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<Coupons> pageResult = couponsService.couponsFindAll(page, size, filters);
		List<CouponsDTOS> coupons = pageResult.getContent().stream().map(couponsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", coupons);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/Coupons/{id}")
	public ResponseEntity<Coupons> getCouponsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		Coupons coupons = (Coupons) couponsService.couponsFindById(id).orElse(new Coupons());
		return ResponseEntity.ok(coupons);
	}

	@PostMapping("/admin/Coupons")
	public ResponseEntity<Coupons> addNewCoupons(@RequestBody CouponsDTOS entity) {

		Coupons saved = couponsService.couponsSave(couponsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/Coupons/{id}")
	public ResponseEntity<?> updateCoupons(@PathVariable int id, @RequestBody CouponsDTOS entity) {
		System.out.println("Update Coupons with ID: " + entity);
		try {
			Coupons isExist = couponsService.couponsFindById(id).orElse(null);
			if (isExist != null) {
				Coupons update = couponsMapper.mapperToObject(entity);
				couponsService.couponsSave(update);
				return ResponseEntity.ok(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/Coupons/{id}")
	public ResponseEntity<Void> deleteCoupons(@PathVariable int id) {
		couponsService.couponsDeleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/Coupons/available")
	public ResponseEntity<?> getAvailableCoupons(
	    @RequestParam(defaultValue = "false") boolean hasCombo,
	    @RequestParam(defaultValue = "false") boolean hasPromotionItems,
	    @RequestParam(defaultValue = "0.0") double normalTotal,
	    @RequestParam(defaultValue = "0.0") double totalPrice,
	    Principal principal
	) {
	    Optional<Accounts> userOpt = accountsRepository.findByEmail(principal.getName());
	    if (userOpt.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy người dùng.");
	    }

	    Accounts currentUser = userOpt.get();

	    Map<String, Integer> rankLevels = Map.of(
	        "ALL", 0, "Đồng", 1, "Bạc", 2, "Vàng", 3,
	        "Platinum", 4, "Kim cương", 5, "VIP", 6
	    );
	    int userLevel = rankLevels.getOrDefault(currentUser.getUserRank(), 0);

	    // ✅ GỌI REPO ĐÃ TỐI ƯU
	    List<Coupons> freeshipList = couponsRepository.findActiveFreeshipCoupons();
	    List<Coupons> discountList = couponsRepository.findActiveDiscountCoupons();

	    List<CouponsDTOS> freeshipCoupons = freeshipList.stream()
	        .filter(coupon -> filterValidCoupon(coupon, currentUser, userLevel, hasCombo, hasPromotionItems, normalTotal, totalPrice))
	        .map(couponsMapper::mapper)
	        .collect(Collectors.toList());

	    List<CouponsDTOS> discountCoupons = discountList.stream()
	        .filter(coupon -> filterValidCoupon(coupon, currentUser, userLevel, hasCombo, hasPromotionItems, normalTotal, totalPrice))
	        .map(couponsMapper::mapper)
	        .collect(Collectors.toList());

	    Map<String, Object> response = new HashMap<>();
	    response.put("freeshipCoupons", freeshipCoupons);
	    response.put("discountCoupons", discountCoupons);

	    return ResponseEntity.ok(response);
	}

	private boolean filterValidCoupon(
		    Coupons coupon,
		    Accounts user,
		    int userLevel,
		    boolean hasCombo,
		    boolean hasPromotionItems,
		    double normalTotal,
		    double totalPrice
		) {
		    if (!coupon.isActive() || coupon.getEndAt().isBefore(LocalDateTime.now()))
		        return false;

		    if (coupon.getQty() <= 0)
		        return false;

		    int couponLevel = switch (coupon.getCustomerGroup()) {
		        case "Đồng" -> 1;
		        case "Bạc" -> 2;
		        case "Vàng" -> 3;
		        case "Platinum" -> 4;
		        case "Kim cương" -> 5;
		        case "VIP" -> 6;
		        default -> 0;
		    };

		    if (userLevel < couponLevel)
		        return false;

		    if (coupon.getUsagePerCustomer() != null && coupon.getUsagePerCustomer() > 0) {
		        long usageCount;

		        if ("FREESHIP".equalsIgnoreCase(coupon.getDiscountType())) {
		        	usageCount = ordersRepository.countFreeshipCouponUsage(Long.valueOf(user.getId()), coupon.getCode());
		        } else {
		            usageCount = ordersRepository.countCouponUsage(Long.valueOf(user.getId()), coupon.getCode());
		        }

		        if (usageCount >= coupon.getUsagePerCustomer()) {
		            return false;
		        }
		    }


		    if ("G-DISCOUNT".equalsIgnoreCase(coupon.getDiscountType())) {
		        boolean hasNormalItems = normalTotal > 0;

		        if (!hasNormalItems)
		            return false;

		        if (coupon.getMinOrderAmount() != null &&
		            normalTotal < coupon.getMinOrderAmount().doubleValue())
		            return false;
		    }

		    if ("FREESHIP".equalsIgnoreCase(coupon.getDiscountType())) {
		        if (coupon.getMinOrderAmount() != null &&
		            totalPrice < coupon.getMinOrderAmount().doubleValue())
		            return false;
		    }

		    return true;
		}

}