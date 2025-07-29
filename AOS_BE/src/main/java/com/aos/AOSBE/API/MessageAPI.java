package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.aos.AOSBE.DTOS.MessageDTOS;
import com.aos.AOSBE.Entity.Message;
import com.aos.AOSBE.Mapper.MessageMapper;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.MessageService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class MessageAPI {
	@Autowired
	private MessageService messageService;
	@Autowired
	private AccountsService accountsService;

	@Autowired
	private MessageMapper messageMapper;

	@GetMapping("/admin/Message")
	public ResponseEntity<?> getAllMessageApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<Message> pageResult = messageService.messageFindAll(page, size, filters);
		List<MessageDTOS> message = pageResult.getContent().stream().map(messageMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", message);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/user/Message")
	public ResponseEntity<?> getAllMessageApiUserRoles(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
		Page<Message> pageResult = messageService.messageFindByAccountEmail(page, size, userEmail);
		List<MessageDTOS> message = pageResult.getContent().stream().map(messageMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", message);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/Message/{id}")
	public ResponseEntity<Message> getMessageByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		Message message = (Message) messageService.messageFindById(id).orElse(new Message());
		return ResponseEntity.ok(message);
	}

	@PostMapping("/admin/Message")
	public ResponseEntity<Message> addNewMessage(@RequestBody MessageDTOS entity) {
		String email = SecurityContextHolder.getContext().getAuthentication().getName();

		entity.setAccounts(email);
		Message saved = messageService.messageSave(messageMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/Message/{id}")
	public ResponseEntity<?> updateMessage(@PathVariable int id, @RequestBody MessageDTOS entity) {
		try {
			Message isExist = messageService.messageFindById(id).orElse(null);
			if (isExist != null) {
				Message update = messageMapper.mapperToObject(entity);
				messageService.messageSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/Message/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable int id) {
		messageService.messageDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
