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
	
	@GetMapping("/Coupons/validate")
	public ResponseEntity<?> validateCoupon(
	        @RequestParam String code,
	        @RequestParam(defaultValue = "false") boolean hasCombo,
	        @RequestParam(defaultValue = "false") boolean hasPromotionItems,
	        Principal principal) {

	    Optional<Coupons> couponOpt = couponsRepository.findByCode(code);

	    if (couponOpt.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mã giảm giá.");
	    }

	    Coupons coupon = couponOpt.get();

	    // 1. Đang hoạt động & chưa hết hạn
	    if (!coupon.isActive() || coupon.getEndAt().isBefore(LocalDateTime.now())) {
	        return ResponseEntity.badRequest().body("Mã giảm giá đã hết hạn hoặc không còn hiệu lực.");
	    }

	    // 2. Số lượng còn
	    if (coupon.getQty() <= 0) {
	        return ResponseEntity.badRequest().body("Mã giảm giá đã hết lượt sử dụng.");
	    }

	    // 3. Lấy user hiện tại
	    String email = principal.getName();
	    Optional<Accounts> userOpt = accountsRepository.findByEmail(email);
	    if (userOpt.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Không tìm thấy người dùng.");
	    }
	    Accounts currentUser = userOpt.get();

	    // 4. Kiểm tra hạng khách hàng
	    Map<String, Integer> rankLevels = Map.of(
	    		"ALL", 0, "Đồng", 1, "Bạc", 2, "Vàng", 3,
		        "Platinum", 4, "Kim cương", 5, "VIP", 6
		    );
		    int couponLevel = rankLevels.getOrDefault(coupon.getCustomerGroup(), 0);
		    int userLevel = rankLevels.getOrDefault(currentUser.getUserRank(), 0);
		    if (userLevel < couponLevel) {
		        return ResponseEntity.badRequest().body("Mã này không áp dụng cho hạng của bạn.");
		    }

		// 5. Check coupon type-specific restrictions
		if ("G-DISCOUNT".equalsIgnoreCase(coupon.getDiscountType())) {
		    if (hasCombo && !coupon.isAllowVoucher()) {
		        return ResponseEntity.badRequest().body("Mã giảm giá không áp dụng cho combo.");
		    }
		    if (hasPromotionItems) {
		        return ResponseEntity.badRequest().body("Mã giảm giá không áp dụng cho sản phẩm đang khuyến mãi.");
		    }
	    }
	    
	    // 5.5. Kiểm tra số lần sử dụng của khách hàng
	    long usageCount = ordersRepository.countCouponUsage(Long.valueOf(currentUser.getId()), code);
	    if (usageCount >= coupon.getUsagePerCustomer()) {
	        return ResponseEntity.badRequest().body("Bạn đã sử dụng mã này đủ số lần cho phép.");
	    }

	    // 6. Trả lại DTO nếu hợp lệ
	    CouponsDTOS dto = couponsMapper.mapper(coupon);
	    return ResponseEntity.ok(dto);
	}

}