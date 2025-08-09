package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

import com.aos.AOSBE.DTOS.ReviewsDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Reviews;
import com.aos.AOSBE.Mapper.ReviewsMapper;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.OrdersService;
import com.aos.AOSBE.Service.ReviewsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ReviewsAPI {
	@Autowired
	private ReviewsService reviewsService;

	@Autowired
	private ReviewsMapper reviewsMapper;

	@Autowired
	private AccountsService accountsService;

	@Autowired
	private OrdersService ordersService;

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
	public ResponseEntity<?> addNewReviews(@RequestBody ReviewsDTOS entity) {
		try {
			// Lấy user từ SecurityContext
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String email = userDetails.getUsername();

			// Lấy account từ service
			Accounts account = accountsService.accountsFindByEmail(email).orElse(null);
			if (account == null) {
				return ResponseEntity.badRequest().body(Map.of("message", "Tài khoản không tồn tại"));
			}

			// Set lại accountId để mapper dùng được
			entity.setAccountId(account.getId());

			Reviews saved = reviewsService.reviewsSave(reviewsMapper.mapperToObject(entity));
			return ResponseEntity.ok(saved);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("message", "Lỗi khi gửi đánh giá", "error", e.getMessage()));
		}
	}

	@PostMapping("/user/Reviews")
	public ResponseEntity<?> addNewReviewsUserRoles(@RequestBody ReviewsDTOS entity) {
		try {
			// Lấy user từ SecurityContext
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String email = userDetails.getUsername();

			// Lấy account từ service
			Accounts account = accountsService.accountsFindByEmail(email).orElse(null);
			if (account == null) {
				return ResponseEntity.badRequest().body(Map.of("message", "Tài khoản không tồn tại"));
			}

			int productItemId = entity.getProductItems();
			int orderId = entity.getOrderId();

			boolean hasReceived = ordersService.hasUserReceivedProduct((long) account.getId(),(long) productItemId
			);
			if (!hasReceived) {
				return ResponseEntity.badRequest().body(
					Map.of("message", "Bạn chỉ có thể đánh giá khi đã mua và nhận sản phẩm.")
				);
			}

			boolean hasReviewed = reviewsService.hasReviewed((long) account.getId(),(long) productItemId,(long) orderId);
			if (hasReviewed) {
				return ResponseEntity.badRequest().body(
					Map.of("message", "Bạn đã đánh giá sản phẩm này trong đơn hàng này.")
				);
			}


			// Set lại accountId để mapper dùng được
			entity.setAccountId(account.getId());

			Reviews saved = reviewsService.reviewsSave(reviewsMapper.mapperToObject(entity));
			return ResponseEntity.ok(saved);

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("message", "Lỗi khi gửi đánh giá", "error", e.getMessage()));
		}
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
	public ResponseEntity<?> getReviewsByProductItemId(@PathVariable Long productItemId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

		Page<Reviews> pageResult = reviewsService.reviewsFindByProductItemId(productItemId, page, size);
		List<ReviewsDTOS> reviewsDTOSList = pageResult.getContent().stream().map(reviewsMapper::mapper)
				.collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("content", reviewsDTOSList);
		response.put("totalPages", pageResult.getTotalPages());
		response.put("totalElements", pageResult.getTotalElements());
		response.put("currentPage", pageResult.getNumber());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/reviews/product/average-rating/{productItemId}")
	public ResponseEntity<?> getAverageRating(@PathVariable Long productItemId) {
		Double average = reviewsService.getAverageRatingByProductItemId(productItemId);
		return ResponseEntity.ok(Map.of("averageRating", average));
	}

	@GetMapping("/reviews/product/count/{productItemId}")
	public ResponseEntity<?> countReviews(@PathVariable Long productItemId) {
		Long count = reviewsService.countReviewsByProductItemId(productItemId);
		return ResponseEntity.ok(Map.of("total", count));
	}

	@GetMapping("/user/reviews/check")
	public ResponseEntity<?> checkReviewed(
		@RequestParam Long productItemId,
		@RequestParam Long orderId
	) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String email = userDetails.getUsername();

			Accounts account = accountsService.accountsFindByEmail(email).orElse(null);
			if (account == null) {
				return ResponseEntity.badRequest().body(Map.of("message", "Tài khoản không tồn tại"));
			}

			boolean hasReviewed = reviewsService.hasReviewed(
				(long) account.getId(),
				productItemId,
				orderId
			);

			return ResponseEntity.ok(Map.of("hasReviewed", hasReviewed));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(Map.of("message", "Lỗi kiểm tra đánh giá", "error", e.getMessage()));
		}
	}
}