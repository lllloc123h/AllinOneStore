package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
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
public class RolesAPI {
	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private RolesMapper rolesMapper;

	@GetMapping("/admin/Roles")
	public ResponseEntity<List<RolesDTOS>> getAllRolesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<RolesDTOS> roles = new ArrayList<RolesDTOS>();
		rolesService.rolesFindAll(page, size).forEach(e -> {
			roles.add(rolesMapper.mapper(e));
		});
		return ResponseEntity.ok(roles);
	}

	@GetMapping("/admin/Roles/{id}")
	public ResponseEntity<Roles> getRolesByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		Roles roles =(Roles)rolesService.rolesFindById(id).orElse(new Roles());
		return ResponseEntity.ok(roles);
	}
	@PostMapping("/admin/Roles")
	public ResponseEntity<Roles> addNewRoles(@RequestBody RolesDTOS entity) {
	    
	    Roles saved = rolesService.rolesSave(rolesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/Roles/{id}")
	public ResponseEntity<?> updateRoles( @PathVariable int id,@RequestBody RolesDTOS entity) {
			try {
			Roles  isExist = rolesService.rolesFindById(id).orElse(null);
			if (isExist != null) {
				Roles  update = rolesMapper.mapperToObject(entity);
				rolesService.rolesSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/Roles/{id}")
	public ResponseEntity<Void> deleteRoles(@PathVariable int id) {
	    rolesService.rolesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
