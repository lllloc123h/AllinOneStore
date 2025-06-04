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
public class NewsAPI {
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private NewsMapper newsMapper;

	@GetMapping("/News")
	public ResponseEntity<List<NewsDTOS>> getAllNewsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<NewsDTOS> news = new ArrayList<NewsDTOS>();
		newsService.newsFindAll(page, size).forEach(e -> {
			news.add(newsMapper.mapper(e));
		});
		return ResponseEntity.ok(news);
	}

	@GetMapping("/News/{id}")
	public ResponseEntity<News> getNewsByIdApi(@PathVariable int id) {
		News news =(News)newsService.newsFindById(id).orElse(new News());
		return ResponseEntity.ok(news);
	}
	@PostMapping("/News")
	public ResponseEntity<News> addNewNews(@RequestBody NewsDTOS entity) {
	    
	    News saved = newsService.newsSave(newsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/News")
	public ResponseEntity<News> updateNews(@RequestBody News entity) {
	    News updated = newsService.newsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/News/{id}")
	public ResponseEntity<Void> deleteNews(@PathVariable int id) {
	    newsService.newsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
