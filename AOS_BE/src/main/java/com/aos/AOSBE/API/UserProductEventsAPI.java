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

import com.aos.AOSBE.DTOS.UserProductEventsDTOS;
import com.aos.AOSBE.Entity.UserProductEvents;
import com.aos.AOSBE.Mapper.UserProductEventsMapper;
import com.aos.AOSBE.Service.UserProductEventsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UserProductEventsAPI {
	@Autowired
	private UserProductEventsService userProductEventsService;

	@Autowired
	private UserProductEventsMapper userProductEventsMapper;

	@GetMapping("/admin/UserProductEvents")
	public ResponseEntity<?> getAllUserProductEventsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<UserProductEvents> pageResult = userProductEventsService.userProductEventsFindAll(page, size, filters);
		List<UserProductEventsDTOS> userProductEvents = pageResult.getContent().stream()
				.map(userProductEventsMapper::mapper).collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", userProductEvents);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/UserProductEvents/{id}")
	public ResponseEntity<UserProductEvents> getUserProductEventsByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		UserProductEvents userProductEvents = (UserProductEvents) userProductEventsService.userProductEventsFindById(id)
				.orElse(new UserProductEvents());
		return ResponseEntity.ok(userProductEvents);
	}

	@PostMapping("/admin/UserProductEvents")
	public ResponseEntity<UserProductEvents> addNewUserProductEvents(@RequestBody UserProductEventsDTOS entity) {

		UserProductEvents saved = userProductEventsService
				.userProductEventsSave(userProductEventsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PostMapping("/UserProductEvents")
	public ResponseEntity<?> catchUserEvent(@RequestBody UserProductEventsDTOS entity) {
		try {
			if (entity.getAccounts().equals("AdminToCountUserCatchEvent")) {
				entity.setAccounts("");
			}
			UserProductEvents map = userProductEventsMapper.mapperToObject(entity);
			UserProductEvents saved = userProductEventsService.userProductEventsSave(map);
			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra" + e.getMessage()));
		}
	}

	@PutMapping("/admin/UserProductEvents/{id}")
	public ResponseEntity<?> updateUserProductEvents(@PathVariable int id, @RequestBody UserProductEventsDTOS entity) {
		try {
			UserProductEvents isExist = userProductEventsService.userProductEventsFindById(id).orElse(null);
			if (isExist != null) {
				UserProductEvents update = userProductEventsMapper.mapperToObject(entity);
				userProductEventsService.userProductEventsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/UserProductEvents/{id}")
	public ResponseEntity<Void> deleteUserProductEvents(@PathVariable int id) {
		userProductEventsService.userProductEventsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
