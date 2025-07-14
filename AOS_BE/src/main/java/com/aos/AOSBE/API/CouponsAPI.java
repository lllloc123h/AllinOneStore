package com.aos.AOSBE.API;

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
import com.aos.AOSBE.Entity.Coupons;
import com.aos.AOSBE.Mapper.CouponsMapper;
import com.aos.AOSBE.Repository.CouponsRepository;
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
		try {
			Coupons isExist = couponsService.couponsFindById(id).orElse(null);
			if (isExist != null) {
				Coupons update = couponsMapper.mapperToObject(entity);
				couponsService.couponsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
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
	public ResponseEntity<?> validateCoupon(@RequestParam String code) {
	    Optional<Coupons> couponOpt = couponsRepository.findByCode(code);

	    if (couponOpt.isEmpty()) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy mã giảm giá.");
	    }

	    Coupons coupon = couponOpt.get();

	    if (!coupon.isActive() || coupon.getEndAt().isBefore(LocalDateTime.now())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Mã giảm giá đã hết hạn hoặc không còn hiệu lực.");
	    }

	    CouponsDTOS dto = couponsMapper.mapper(coupon);
	    return ResponseEntity.ok(dto);
	}

}