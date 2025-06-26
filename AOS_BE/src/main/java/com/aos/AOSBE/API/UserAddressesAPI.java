package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.aos.AOSBE.DTOS.UserAddressesDTOS;
import com.aos.AOSBE.Entity.UserAddresses;
import com.aos.AOSBE.Mapper.UserAddressesMapper;
import com.aos.AOSBE.Service.UserAddressesService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserAddressesAPI {
	@Autowired
	private UserAddressesService userAddressesService;

	@Autowired
	private UserAddressesMapper userAddressesMapper;

	@GetMapping("/admin/UserAddresses")
	public ResponseEntity<List<UserAddressesDTOS>> getAllUserAddressesApiAdminRoles(
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {

		List<UserAddressesDTOS> userAddresses = new ArrayList<UserAddressesDTOS>();
		userAddressesService.userAddressesFindAll(page, size).forEach(e -> {
			userAddresses.add(userAddressesMapper.mapper(e));
		});
		return ResponseEntity.ok(userAddresses);
	}

	@GetMapping("/UserAddresses")
	public ResponseEntity<?> getAllUserAddressesApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			List<UserAddressesDTOS> userAddresses = new ArrayList<UserAddressesDTOS>();
			userAddressesService.userAddressesFindAllByUserEmail(userEmail).forEach(e -> {
				userAddresses.add(userAddressesMapper.mapper(e));
			});
			return ResponseEntity.ok(userAddresses);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "đã có lỗi xảy ra"));
		}

	}

	@DeleteMapping("/UserAddresses/{id}")
	public ResponseEntity<Void> deleteUserAddresses(@PathVariable int id) {
		userAddressesService.userAddressesDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/admin/UserAddresses/{id}")
	public ResponseEntity<UserAddresses> getUserAddressesByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		UserAddresses userAddresses = (UserAddresses) userAddressesService.userAddressesFindById(id)
				.orElse(new UserAddresses());
		return ResponseEntity.ok(userAddresses);
	}

	@PostMapping("/admin/UserAddresses")
	public ResponseEntity<UserAddresses> addNewUserAddressesAdminRoles(@RequestBody UserAddressesDTOS entity) {

		UserAddresses saved = userAddressesService.userAddressesSave(userAddressesMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PostMapping("/UserAddresses")
	public ResponseEntity<?> addNewUserAddresses(@RequestBody UserAddressesDTOS entity) {
		try {
			String email = SecurityContextHolder.getContext().getAuthentication().getName();
			entity.setAccounts(email);
			UserAddresses saved = userAddressesService.userAddressesSave(userAddressesMapper.mapperToObject(entity));
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Error occurred"));
		}
	}

	@PutMapping("/UserAddresses/{id}")
	public ResponseEntity<?> setDefaultAddress(@PathVariable int id) {
		try {
			UserAddresses address = userAddressesService.userAddressesFindById(id).orElse(null);
			if (address != null) {
				UserAddresses updated = userAddressesService.userAddressesSetDefaultAddress(id, address);
				return ResponseEntity.ok().body(Map.of("message", "Set as default successfully"));
			} else {
				return ResponseEntity.badRequest().body(Map.of("message", "Address not found"));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Error occurred"));
		}
	}

	@PutMapping("/admin/UserAddresses/{id}")
	public ResponseEntity<?> updateUserAddressesAdminRoles(@PathVariable int id,
			@RequestBody UserAddressesDTOS entity) {
		try {
			UserAddresses isExist = userAddressesService.userAddressesFindById(id).orElse(null);
			if (isExist != null) {
				UserAddresses update = userAddressesMapper.mapperToObject(entity);
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
	public ResponseEntity<Void> deleteUserAddressesAdminRoles(@PathVariable int id) {
		userAddressesService.userAddressesDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
