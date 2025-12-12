package com.aos.AOSBE.API;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

import com.aos.AOSBE.DTOS.EmailTEGVDTOS;
import com.aos.AOSBE.Entity.EmailTEGV;
import com.aos.AOSBE.Mapper.EmailTEGVMapper;
import com.aos.AOSBE.Service.EmailService;
import com.aos.AOSBE.Service.EmailTEGVService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class EmailTEGVAPI {
	@Autowired
	private EmailTEGVService EmailTEGVService;

	@Autowired
	private EmailService emailService;
	@Autowired
	private EmailTEGVMapper EmailTEGVMapper;

	@GetMapping("/admin/EmailTEGV")
	public ResponseEntity<?> getAllEmailTEGVApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<EmailTEGV> pageResult = EmailTEGVService.EmailTEGVFindAll(page, size, filters);
		List<EmailTEGVDTOS> EmailTEGV = pageResult.getContent().stream().map(EmailTEGVMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", EmailTEGV);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);
	}

	@GetMapping("/admin/EmailTEGV/{id}")
	public ResponseEntity<EmailTEGV> getEmailTEGVByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		EmailTEGV EmailTEGV = (EmailTEGV) EmailTEGVService.EmailTEGVFindById(id).orElse(new EmailTEGV());
		return ResponseEntity.ok(EmailTEGV);
	}

	@PostMapping("/admin/EmailTEGV")
	public ResponseEntity<EmailTEGV> addNewEmailTEGV(@RequestBody EmailTEGVDTOS entity) {

		EmailTEGV saved = EmailTEGVService.EmailTEGVSave(EmailTEGVMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/EmailTEGV/{id}")
	public ResponseEntity<?> updateEmailTEGV(@PathVariable int id, @RequestBody EmailTEGVDTOS entity) {
		try {
			EmailTEGV isExist = EmailTEGVService.EmailTEGVFindById(id).orElse(null);
			if (isExist != null) {
				EmailTEGV update = EmailTEGVMapper.mapperToObject(entity);
				EmailTEGVService.EmailTEGVSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@DeleteMapping("/admin/EmailTEGV/{id}")
	public ResponseEntity<Void> deleteEmailTEGV(@PathVariable int id) {
		EmailTEGVService.EmailTEGVDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/admin/EmailTEGV/import")
	public ResponseEntity<?> importEmailTEGV(@RequestParam("file") MultipartFile file) {
		try {
			String message = EmailTEGVService.importExcel(file);
			return ResponseEntity.ok(Map.of("message", message));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		}
	}

	@PostMapping("/admin/send-with-images")
	public ResponseEntity<?> sendEmailWithImages(@RequestParam("subject") String subject,
			@RequestParam("message") String message,
			@RequestParam(value = "images", required = false) MultipartFile[] images) {
		try {
			List<EmailTEGV> listMail = EmailTEGVService.EmailTEGVFindAllNotUsingPage();
			for (EmailTEGV spamEmail : listMail) {
				emailService.sendEmailWithImages(spamEmail.getEmailTEGV(), subject, message, images);
			}
			return ResponseEntity.ok("Email sent successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to send email: " + e.getMessage());
		}
	}
}
