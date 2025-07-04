package com.aos.AOSBE.API;

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

import com.aos.AOSBE.DTOS.NewsDTOS;
import com.aos.AOSBE.Entity.News;
import com.aos.AOSBE.Mapper.NewsMapper;
import com.aos.AOSBE.Service.NewsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class NewsAPI {
	@Autowired
	private NewsService newsService;

	@Autowired
	private NewsMapper newsMapper;

	@GetMapping("/admin/News")
	public ResponseEntity<?> getAllNewsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<News> pageResult = newsService.newsFindAll(page, size, filters);
		List<NewsDTOS> news = pageResult.getContent().stream().map(newsMapper::mapper).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", news);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/News/{id}")
	public ResponseEntity<News> getNewsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		News news = (News) newsService.newsFindById(id).orElse(new News());
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
			News isExist = newsService.newsFindById(id).orElse(null);
			if (isExist != null) {
				News update = newsMapper.mapperToObject(entity);
				newsService.newsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/News/{id}")
	public ResponseEntity<Void> deleteNews(@PathVariable int id) {
		newsService.newsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}