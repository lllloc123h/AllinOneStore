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
public class UserLogsAPI {
	@Autowired
	private UserLogsService userLogsService;
	private UserLogsMapper userLogsMapper=new UserLogsMapper();

	@GetMapping("/UserLogs")
	public ResponseEntity<List<UserLogsDTOS>> getAllUserLogsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<UserLogsDTOS> userLogs = new ArrayList<UserLogsDTOS>();
		userLogsService.userLogsFindAll(page, size).forEach(e -> {
			userLogs.add(userLogsMapper.mapper(e));
		});
		return ResponseEntity.ok(userLogs);
	}

	@GetMapping("/UserLogs/{id}")
	public ResponseEntity<UserLogs> getUserLogsByIdApi(@PathVariable int id) {
		UserLogs userLogs =(UserLogs)userLogsService.userLogsFindById(id).orElse(new UserLogs());
		return ResponseEntity.ok(userLogs);
	}
	@PostMapping("/UserLogs")
	public ResponseEntity<UserLogs> addNewUserLogs(@RequestBody UserLogsDTOS entity) {
	    
	    UserLogs saved = userLogsService.userLogsSave(userLogsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/UserLogs")
	public ResponseEntity<UserLogs> updateUserLogs(@RequestBody UserLogs entity) {
	    UserLogs updated = userLogsService.userLogsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/UserLogs/{id}")
	public ResponseEntity<Void> deleteUserLogs(@PathVariable int id) {
	    userLogsService.userLogsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
