package com.aos.AOSBE.API;

import java.util.List;

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
public class UserAddressesAPI {
	@Autowired
	private UserAddressesService userAddressesService;
	private UserAddressesMapper userAddressesMapper=new UserAddressesMapper();

	@GetMapping("/UserAddresses")
	public ResponseEntity<List<UserAddressesDTOS>> getAllUserAddressesApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<UserAddressesDTOS> userAddresses = new ArrayList<UserAddressesDTOS>();
		userAddressesService.userAddressesFindAll(page, size).forEach(e -> {
			userAddresses.add(userAddressesMapper.mapper(e));
		});
		return ResponseEntity.ok(userAddresses);
	}

	@GetMapping("/UserAddresses/{id}")
	public ResponseEntity<UserAddresses> getUserAddressesByIdApi(@PathVariable int id) {
		UserAddresses userAddresses =(UserAddresses)userAddressesService.userAddressesFindById(id).orElse(new UserAddresses());
		return ResponseEntity.ok(userAddresses);
	}
	@PostMapping("/UserAddresses")
	public ResponseEntity<UserAddresses> addNewUserAddresses(@RequestBody UserAddresses entity) {
	    UserAddresses saved = userAddressesService.userAddressesSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/UserAddresses")
	public ResponseEntity<UserAddresses> updateUserAddresses(@RequestBody UserAddresses entity) {
	    UserAddresses updated = userAddressesService.userAddressesSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/UserAddresses/{id}")
	public ResponseEntity<Void> deleteUserAddresses(@PathVariable int id) {
	    userAddressesService.userAddressesDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
