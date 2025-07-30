package com.aos.AOSBE.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.DTOS.ChatDTO;
import com.aos.AOSBE.Service.OpenAIService;

import jakarta.servlet.http.HttpSession;

@RestController()
@RequestMapping("/api")
@CrossOrigin(origins = { "http://localhost:5173", "https://allinstore.me" })
public class OpenAI {
	// This class will handle OpenAI API requests
	// You can define endpoints here to interact with OpenAI services
	// For example, you might have methods to send prompts and receive responses
	// using OpenAI's API client.
	@Autowired
	private OpenAIService openAIService; // Assuming you have a service to handle OpenAI API calls
	@Autowired
	private HttpSession session; // To manage user sessions if needed
//     Example endpoint (to be implemented):

	@PostMapping("/openai/chat")
	public ResponseEntity<?> chatWithOpenAI(@RequestBody ChatDTO chat) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println("User Email ở OPEN AI: " + userEmail);
			String conversationId = "";
			if (userEmail.equals("anonymousUser")) {
				conversationId = session.getId();
			} else {
				conversationId = userEmail;
			}
			// Logic to call OpenAI API and return response
			String response = openAIService.userChatBot(chat.getMessage(), conversationId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			return ResponseEntity.status(500).body(null);
		}
	}

	@PostMapping("/openai/forecast")
	public ResponseEntity<?> forecastWithOpenAI(@RequestBody ChatDTO chat) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println("User Email ở OPEN AI: " + userEmail);
			String conversationId = "";
			if (userEmail.contains("anonymousUser")) {
				conversationId = session.getId();
			} else {
				conversationId = userEmail;
			}
			// Logic to call OpenAI API and return response
			String response = openAIService.forecastChatBot(chat.getMessage(), conversationId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}

	@PostMapping("/openai/personalProducts")
	public ResponseEntity<?> personalProductsWithOpenAI(@RequestBody ChatDTO chat) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			System.out.println("User Email ở OPEN AI: " + userEmail);
			String conversationId = "";
			if (userEmail.contains("anonymousUser")) {
				conversationId = session.getId();
			} else {
				conversationId = userEmail;
			}
			// Logic to call OpenAI API and return response
			String response = openAIService.personalProductChatBot(chat.getMessage(), conversationId);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			e.getStackTrace();
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}
