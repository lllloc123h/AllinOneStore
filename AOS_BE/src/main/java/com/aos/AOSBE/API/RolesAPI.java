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
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class RolesAPI {
	@Autowired
	private RolesService rolesService;
	private RolesMapper rolesMapper=new RolesMapper();

	@GetMapping("/Roles")
	public ResponseEntity<List<RolesDTOS>> getAllRolesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<RolesDTOS> roles = new ArrayList<RolesDTOS>();
		rolesService.rolesFindAll(page, size).forEach(e -> {
			roles.add(rolesMapper.mapper(e));
		});
		return ResponseEntity.ok(roles);
	}

	@GetMapping("/Roles/{id}")
	public ResponseEntity<Roles> getRolesByIdApi(@PathVariable int id) {
		Roles roles =(Roles)rolesService.rolesFindById(id).orElse(new Roles());
		return ResponseEntity.ok(roles);
	}
	@PostMapping("/Roles")
	public ResponseEntity<Roles> addNewRoles(@RequestBody Roles entity) {
	    Roles saved = rolesService.rolesSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Roles")
	public ResponseEntity<Roles> updateRoles(@RequestBody Roles entity) {
	    Roles updated = rolesService.rolesSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Roles/{id}")
	public ResponseEntity<Void> deleteRoles(@PathVariable int id) {
	    rolesService.rolesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
