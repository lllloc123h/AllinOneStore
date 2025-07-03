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

import com.aos.AOSBE.DTOS.AuthoritiesDTOS;
import com.aos.AOSBE.Entity.Authorities;
import com.aos.AOSBE.Mapper.AuthoritiesMapper;
import com.aos.AOSBE.Service.AuthoritiesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthoritiesAPI {
	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	private AuthoritiesMapper authoritiesMapper;

	@GetMapping("/admin/Authorities")
	public ResponseEntity<?> getAllAuthoritiesApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<Authorities> pageResult = authoritiesService.authoritiesFindAll(page, size, filters);
		List<AuthoritiesDTOS> authorities = pageResult.getContent().stream().map(authoritiesMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", authorities);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/Authorities/{id}")
	public ResponseEntity<Authorities> getAuthoritiesByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		Authorities authorities = (Authorities) authoritiesService.authoritiesFindById(id).orElse(new Authorities());
		return ResponseEntity.ok(authorities);
	}

	@PostMapping("/admin/Authorities")
	public ResponseEntity<Authorities> addNewAuthorities(@RequestBody AuthoritiesDTOS entity) {

		Authorities saved = authoritiesService.authoritiesSave(authoritiesMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/Authorities/{id}")
	public ResponseEntity<?> updateAuthorities(@PathVariable int id, @RequestBody AuthoritiesDTOS entity) {
		try {
			Authorities isExist = authoritiesService.authoritiesFindById(id).orElse(null);
			if (isExist != null) {
				Authorities update = authoritiesMapper.mapperToObject(entity);
				authoritiesService.authoritiesSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/Authorities/{id}")
	public ResponseEntity<Void> deleteAuthorities(@PathVariable int id) {
		authoritiesService.authoritiesDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}