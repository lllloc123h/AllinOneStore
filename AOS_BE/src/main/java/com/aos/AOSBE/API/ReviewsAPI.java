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
public class ReviewsAPI {
	@Autowired
	private ReviewsService reviewsService;
	
	@Autowired
	private ReviewsMapper reviewsMapper;

	@GetMapping("/admin/Reviews")
	public ResponseEntity<List<ReviewsDTOS>> getAllReviewsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<ReviewsDTOS> reviews = new ArrayList<ReviewsDTOS>();
		reviewsService.reviewsFindAll(page, size).forEach(e -> {
			reviews.add(reviewsMapper.mapper(e));
		});
		return ResponseEntity.ok(reviews);
	}

	@GetMapping("/admin/Reviews/{id}")
	public ResponseEntity<Reviews> getReviewsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Reviews reviews =(Reviews)reviewsService.reviewsFindById(id).orElse(new Reviews());
		return ResponseEntity.ok(reviews);
	}
	@PostMapping("/admin/Reviews")
	public ResponseEntity<Reviews> addNewReviews(@RequestBody ReviewsDTOS entity) {
	    
	    Reviews saved = reviewsService.reviewsSave(reviewsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Reviews")
	public ResponseEntity<?> updateReviews(@RequestBody Reviews entity) {
			try {
			Reviews  isExist = reviewsService.reviewsFindById(id).orElse(null);
			if (isExist != null) {
				Reviews  update = reviewsMapper.mapperToObject(entity);
				reviewsService.reviewsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/Reviews/{id}")
	public ResponseEntity<Void> deleteReviews(@PathVariable int id) {
	    reviewsService.reviewsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
