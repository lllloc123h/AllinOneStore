package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
public class CouponsAPI {
	@Autowired
	private CouponsService couponsService;
	
	@Autowired
	private CouponsMapper couponsMapper;

	@GetMapping("/admin/Coupons")
	public ResponseEntity<List<CouponsDTOS>> getAllCouponsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CouponsDTOS> coupons = new ArrayList<CouponsDTOS>();
		couponsService.couponsFindAll(page, size).forEach(e -> {
			coupons.add(couponsMapper.mapper(e));
		});
		return ResponseEntity.ok(coupons);
	}

	@GetMapping("/admin/Coupons/{id}")
	public ResponseEntity<Coupons> getCouponsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Coupons coupons =(Coupons)couponsService.couponsFindById(id).orElse(new Coupons());
		return ResponseEntity.ok(coupons);
	}
	@PostMapping("/admin/Coupons")
	public ResponseEntity<Coupons> addNewCoupons(@RequestBody CouponsDTOS entity) {
	    
	    Coupons saved = couponsService.couponsSave(couponsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Coupons/{id}")
	public ResponseEntity<?> updateCoupons( @PathVariable int id,@RequestBody CouponsDTOS entity) {
			try {
			Coupons  isExist = couponsService.couponsFindById(id).orElse(null);
			if (isExist != null) {
				Coupons  update = couponsMapper.mapperToObject(entity);
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


	
}
