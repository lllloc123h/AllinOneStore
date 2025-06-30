//package com.aos.AOSBE.API;
//
//import java.util.List;
//import java.util.ArrayList;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.data.domain.PageRequest;
//import com.aos.AOSBE.Entity.*;
//import com.aos.AOSBE.Service.*;
//import com.aos.AOSBE.DTOS.*;
//import com.aos.AOSBE.Mapper.*;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//
//@RestController
//@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:5173")
//public class SysdiagramsAPI {
//	@Autowired
//	private SysdiagramsService sysdiagramsService;
//	
//	@Autowired
//	private SysdiagramsMapper sysdiagramsMapper;
//
//	@GetMapping("/admin/Sysdiagrams")
//	public ResponseEntity<List<SysdiagramsDTOS>> getAllSysdiagramsApi(	
//			@RequestParam(defaultValue = "0") int page,
//			@RequestParam(defaultValue = "5") int size) {
//			
//		List<SysdiagramsDTOS> sysdiagrams = new ArrayList<SysdiagramsDTOS>();
//		sysdiagramsService.sysdiagramsFindAll(page, size, filters).forEach(e -> {
//			sysdiagrams.add(sysdiagramsMapper.mapper(e));
//		});
//		return ResponseEntity.ok(sysdiagrams);
//	}
//
//	@GetMapping("/admin/Sysdiagrams/{id}")
//	public ResponseEntity<Sysdiagrams> getSysdiagramsByIdApi(@PathVariable int id) {
//		//try{
//		//}catch(Exception e){
//		//}
//		
//		Sysdiagrams sysdiagrams =(Sysdiagrams)sysdiagramsService.sysdiagramsFindById(id).orElse(new Sysdiagrams());
//		return ResponseEntity.ok(sysdiagrams);
//	}
//	@PostMapping("/admin/Sysdiagrams")
//	public ResponseEntity<Sysdiagrams> addNewSysdiagrams(@RequestBody SysdiagramsDTOS entity) {
//	    
//	    Sysdiagrams saved = sysdiagramsService.sysdiagramsSave(sysdiagramsMapper.mapperToObject(entity));	    
//	    return ResponseEntity.ok(saved);
//	}
//	@PutMapping("/admin/Sysdiagrams/{id}")
//	public ResponseEntity<?> updateSysdiagrams( @PathVariable int id,@RequestBody SysdiagramsDTOS entity) {
//			try {
//			Sysdiagrams  isExist = sysdiagramsService.sysdiagramsFindById(id).orElse(null);
//			if (isExist != null) {
//				Sysdiagrams  update = sysdiagramsMapper.mapperToObject(entity);
//				sysdiagramsService.sysdiagramsSave(update);
//				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
//			} else {
//				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
//		} 
//	}
//	@DeleteMapping("/admin/Sysdiagrams/{id}")
//	public ResponseEntity<Void> deleteSysdiagrams(@PathVariable int id) {
//	    sysdiagramsService.sysdiagramsDeleteById(id); 
//	    return ResponseEntity.noContent().build(); 
//	}
//
//
//	
//}