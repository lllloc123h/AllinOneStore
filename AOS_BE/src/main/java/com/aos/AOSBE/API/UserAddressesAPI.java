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
public class UserAddressesAPI {
	@Autowired
	private UserAddressesService userAddressesService;
	
	@Autowired
	private UserAddressesMapper userAddressesMapper;

	@GetMapping("/admin/UserAddresses")
	public ResponseEntity<List<UserAddressesDTOS>> getAllUserAddressesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<UserAddressesDTOS> userAddresses = new ArrayList<UserAddressesDTOS>();
		userAddressesService.userAddressesFindAll(page, size).forEach(e -> {
			userAddresses.add(userAddressesMapper.mapper(e));
		});
		return ResponseEntity.ok(userAddresses);
	}

	@GetMapping("/admin/UserAddresses/{id}")
	public ResponseEntity<UserAddresses> getUserAddressesByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		UserAddresses userAddresses =(UserAddresses)userAddressesService.userAddressesFindById(id).orElse(new UserAddresses());
		return ResponseEntity.ok(userAddresses);
	}
	@PostMapping("/admin/UserAddresses")
	public ResponseEntity<UserAddresses> addNewUserAddresses(@RequestBody UserAddressesDTOS entity) {
	    
	    UserAddresses saved = userAddressesService.userAddressesSave(userAddressesMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/UserAddresses/{id}")
	public ResponseEntity<?> updateUserAddresses( @PathVariable int id,@RequestBody UserAddressesDTOS entity) {
			try {
			UserAddresses  isExist = userAddressesService.userAddressesFindById(id).orElse(null);
			if (isExist != null) {
				UserAddresses  update = userAddressesMapper.mapperToObject(entity);
				userAddressesService.userAddressesSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/UserAddresses/{id}")
	public ResponseEntity<Void> deleteUserAddresses(@PathVariable int id) {
	    userAddressesService.userAddressesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
