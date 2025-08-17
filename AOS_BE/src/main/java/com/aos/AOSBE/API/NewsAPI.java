package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.aos.AOSBE.DTOS.NewsDTOS;
import com.aos.AOSBE.DTOS.FilterNews; // Import FilterNews DTO
import com.aos.AOSBE.Entity.News;
import com.aos.AOSBE.Mapper.NewsMapper;
import com.aos.AOSBE.Service.NewsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173") // Cấu hình CORS cho frontend của bạn
public class NewsAPI {

	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsMapper newsMapper;

	@GetMapping("/admin/News")
	public ResponseEntity<?> getAllNewsApiAminRoles(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		// Không cần các dòng filters.remove("page"); filters.remove("size"); nữa
		// vì Spring Boot đã tự động tách chúng ra và truyền vào @RequestParam page, size

		Page<News> pageResult = newsService.newsFindAll(page, size, filters); // Truyền trực tiếp FilterNews DTO
		List<NewsDTOS> news = pageResult.getContent().stream().map(newsMapper::mapper).collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("content", news);
		response.put("totalPages", pageResult.getTotalPages());
		response.put("totalElements", pageResult.getTotalElements()); // Thêm totalElements để client tiện hiển thị
		response.put("currentPage", pageResult.getNumber()); // Thêm số trang hiện tại
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/News/{id}")
	public ResponseEntity<News> getNewsByIdApi(@PathVariable int id) {
		News news = newsService.newsFindById(id).orElse(null); // Sử dụng orElse(null) thay vì new News() nếu muốn trả về 404
		if (news == null) {
			return ResponseEntity.notFound().build(); // Trả về 404 Not Found nếu không tìm thấy
		}
		return ResponseEntity.ok(news);
	}

	@PostMapping("/admin/News")
	public ResponseEntity<News> addNewNews(@RequestBody NewsDTOS entity) {
		News saved = newsService.newsSave(newsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/News/{id}")
	public ResponseEntity<?> updateNews(@PathVariable int id, @RequestBody NewsDTOS entity) {
		try {
			News existingNews = newsService.newsFindById(id).orElse(null);
			if (existingNews != null) {
				// Cập nhật các trường của existingNews từ entity DTO
				// Tránh tạo một đối tượng News mới hoàn toàn để không mất các trường @CreationTimestamp
				News updatedNews = newsMapper.mapperToObject(entity); // Ánh xạ từ DTO
				updatedNews.setId(id); // Đảm bảo ID được giữ nguyên cho việc update
				updatedNews.setCreatedAt(existingNews.getCreatedAt()); // Giữ nguyên createdAt

				News saved = newsService.newsSave(updatedNews);
				// Sửa chính tả "measage" thành "message", "successfuly" thành "successfully"
				return ResponseEntity.ok().body(Map.of("message", "Update successfully", "update", saved)); // Trả về 200 OK cho update thành công
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "News not found with ID: " + id)); // Trả về 404 nếu không tìm thấy
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(Map.of("message", "An error occurred during update: " + e.getMessage())); // Trả về 500 Internal Server Error
		}
	}

	@DeleteMapping("/admin/News/{id}")
	public ResponseEntity<Void> deleteNews(@PathVariable int id) {
		if (newsService.newsFindById(id).isPresent()) { // Kiểm tra sự tồn tại trước khi xóa
			newsService.newsDeleteById(id);
			return ResponseEntity.noContent().build(); // Trả về 204 No Content
		} else {
			return ResponseEntity.notFound().build(); // Trả về 404 nếu không tìm thấy để xóa
		}
	}

	// Đổi tên để tránh trùng lặp với /admin/News nếu không có sự khác biệt rõ ràng
	// Hoặc bạn có thể thêm logic lọc khác biệt cho người dùng nếu cần
	@GetMapping("/News")
	public ResponseEntity<?> getAllNewsApiUser(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size,
			@RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<News> pageResult = newsService.newsFindAll(page, size, filters); // Truyền trực tiếp FilterNews DTO
		List<NewsDTOS> news = pageResult.getContent().stream().map(newsMapper::mapper).collect(Collectors.toList());

		Map<String, Object> response = new HashMap<>();
		response.put("content", news);
		response.put("totalPages", pageResult.getTotalPages());
		response.put("totalElements", pageResult.getTotalElements());
		response.put("currentPage", pageResult.getNumber());
		return ResponseEntity.ok(response);
	}
	@GetMapping("/News/{id}")
	public ResponseEntity<?> getNewsByIdApiUser(@PathVariable int id) {
		News news = newsService.newsFindById(id).orElse(null);
		if (news == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(newsMapper.mapper(news));
	}
	@GetMapping("/News/home")
	public ResponseEntity<?> getHomeNews(
		) {
		List<News> newsList = newsService.findLastedNews();
		List<NewsDTOS> news = newsList.stream().map(newsMapper::mapper).collect(Collectors.toList());
		return ResponseEntity.ok(news);
	}
}