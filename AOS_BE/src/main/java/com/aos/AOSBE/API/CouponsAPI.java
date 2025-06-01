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
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class CouponsAPI {
	@Autowired
	private CouponsService couponsService;
	private CouponsMapper couponsMapper=new CouponsMapper();

	@GetMapping("/Coupons")
	public ResponseEntity<List<CouponsDTOS>> getAllCouponsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CouponsDTOS> coupons = new ArrayList<CouponsDTOS>();
		couponsService.couponsFindAll(page, size).forEach(e -> {
			coupons.add(couponsMapper.mapper(e));
		});
		return ResponseEntity.ok(coupons);
	}

	@GetMapping("/Coupons/{id}")
	public ResponseEntity<Coupons> getCouponsByIdApi(@PathVariable int id) {
		Coupons coupons =(Coupons)couponsService.couponsFindById(id).orElse(new Coupons());
		return ResponseEntity.ok(coupons);
	}
	@PostMapping("/Coupons")
	public ResponseEntity<Coupons> addNewCoupons(@RequestBody CouponsDTOS entity) {
	    
	    Coupons saved = couponsService.couponsSave(couponsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Coupons")
	public ResponseEntity<Coupons> updateCoupons(@RequestBody Coupons entity) {
	    Coupons updated = couponsService.couponsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Coupons/{id}")
	public ResponseEntity<Void> deleteCoupons(@PathVariable int id) {
	    couponsService.couponsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
