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
public class AuthoritiesAPI {
	@Autowired
	private AuthoritiesService authoritiesService;
	
	@Autowired
	private AuthoritiesMapper authoritiesMapper;

	@GetMapping("/admin/Authorities")
	public ResponseEntity<List<AuthoritiesDTOS>> getAllAuthoritiesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<AuthoritiesDTOS> authorities = new ArrayList<AuthoritiesDTOS>();
		authoritiesService.authoritiesFindAll(page, size).forEach(e -> {
			authorities.add(authoritiesMapper.mapper(e));
		});
		return ResponseEntity.ok(authorities);
	}

	@GetMapping("/admin/Authorities/{id}")
	public ResponseEntity<Authorities> getAuthoritiesByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Authorities authorities =(Authorities)authoritiesService.authoritiesFindById(id).orElse(new Authorities());
		return ResponseEntity.ok(authorities);
	}
	@PostMapping("/admin/Authorities")
	public ResponseEntity<Authorities> addNewAuthorities(@RequestBody AuthoritiesDTOS entity) {
	    
	    Authorities saved = authoritiesService.authoritiesSave(authoritiesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Authorities")
	public ResponseEntity<?> updateAuthorities(@RequestBody Authorities entity) {
			try {
			Authorities  isExist = authoritiesService.authoritiesFindById(id).orElse(null);
			if (isExist != null) {
				Authorities  update = authoritiesMapper.mapperToObject(entity);
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
