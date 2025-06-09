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
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewsAPI {
	@Autowired
	private ReviewsService reviewsService;
	
	@Autowired
	private ReviewsMapper reviewsMapper;

	@GetMapping("/Reviews")
	public ResponseEntity<List<ReviewsDTOS>> getAllReviewsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<ReviewsDTOS> reviews = new ArrayList<ReviewsDTOS>();
		reviewsService.reviewsFindAll(page, size).forEach(e -> {
			reviews.add(reviewsMapper.mapper(e));
		});
		return ResponseEntity.ok(reviews);
	}

	@GetMapping("/Reviews/{id}")
	public ResponseEntity<Reviews> getReviewsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Reviews reviews =(Reviews)reviewsService.reviewsFindById(id).orElse(new Reviews());
		return ResponseEntity.ok(reviews);
	}
	@PostMapping("/Reviews")
	public ResponseEntity<Reviews> addNewReviews(@RequestBody ReviewsDTOS entity) {
	    
	    Reviews saved = reviewsService.reviewsSave(reviewsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/Reviews")
	public ResponseEntity<Reviews> updateReviews(@RequestBody Reviews entity) {
	    Reviews updated = reviewsService.reviewsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Reviews/{id}")
	public ResponseEntity<Void> deleteReviews(@PathVariable int id) {
	    reviewsService.reviewsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
