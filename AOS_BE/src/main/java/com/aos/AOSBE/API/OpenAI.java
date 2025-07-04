package com.aos.AOSBE.API;

import com.aos.AOSBE.DTOS.ChatDTO;
import com.aos.AOSBE.Service.OpenAIService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class OpenAI {
    // This class will handle OpenAI API requests
    // You can define endpoints here to interact with OpenAI services
    // For example, you might have methods to send prompts and receive responses
    // using OpenAI's API client.
    @Autowired
    private OpenAIService openAIService; // Assuming you have a service to handle OpenAI API calls
//     Example endpoint (to be implemented):
     @PostMapping("/openai/chat")
     public ResponseEntity<?> chatWithOpenAI(@RequestBody ChatDTO chat) {
        try {
            // Logic to call OpenAI API and return response
            String response = openAIService.chatWithGPT(chat.getMessage());
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
     }
}
