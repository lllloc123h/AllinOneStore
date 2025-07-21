package com.aos.AOSBE.API;

import java.nio.file.StandardCopyOption;
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
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.util.StringUtils;

import com.aos.AOSBE.DTOS.ReviewsDTOS;
import com.aos.AOSBE.Entity.Reviews;
import com.aos.AOSBE.Mapper.ReviewsMapper;
import com.aos.AOSBE.Service.ReviewsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewsAPI {
	@Autowired
	private ReviewsService reviewsService;

	@Autowired
	private ReviewsMapper reviewsMapper;

	@GetMapping("/admin/Reviews")
	public ResponseEntity<?> getAllReviewsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<Reviews> pageResult = reviewsService.reviewsFindAll(page, size, filters);
		List<ReviewsDTOS> reviews = pageResult.getContent().stream().map(reviewsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", reviews);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/Reviews/{id}")
	public ResponseEntity<Reviews> getReviewsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		Reviews reviews = (Reviews) reviewsService.reviewsFindById(id).orElse(new Reviews());
		return ResponseEntity.ok(reviews);
	}

	@PostMapping("/admin/Reviews")
	public ResponseEntity<Reviews> addNewReviews(@RequestBody ReviewsDTOS entity) {

		Reviews saved = reviewsService.reviewsSave(reviewsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/Reviews/{id}")
	public ResponseEntity<?> updateReviews(@PathVariable int id, @RequestBody ReviewsDTOS entity) {
		try {
			Reviews isExist = reviewsService.reviewsFindById(id).orElse(null);
			if (isExist != null) {
				Reviews update = reviewsMapper.mapperToObject(entity);
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
	@GetMapping("/reviews/product/{productItemId}")
	public ResponseEntity<?> getReviewsByProductItemId(
			@PathVariable Long productItemId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		
		Page<Reviews> pageResult = reviewsService.reviewsFindByProductItemId(productItemId, page, size);
		List<ReviewsDTOS> reviewsDTOSList = pageResult.getContent().stream()
				.map(reviewsMapper::mapper)
				.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("content", reviewsDTOSList);
		response.put("totalPages", pageResult.getTotalPages());
		response.put("totalElements", pageResult.getTotalElements());
		response.put("currentPage", pageResult.getNumber());

		return ResponseEntity.ok(response);
	}
	@PostMapping("/upload/review-media")
	public ResponseEntity<?> uploadReviewMedia(@RequestParam("file") MultipartFile file) {
		try {
			String uploadDir = "uploads/reviews"; // thư mục gốc
			String filename = StringUtils.cleanPath(file.getOriginalFilename());

			// Tạo thư mục nếu chưa có
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}

			// Lưu file vào thư mục
			Path filePath = uploadPath.resolve(filename);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			// Trả về URL để frontend sử dụng
			String fileUrl = "/uploads/reviews/" + filename;
			return ResponseEntity.ok(Map.of("url", fileUrl));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("error", "Không thể upload file"));
		}
	}
}