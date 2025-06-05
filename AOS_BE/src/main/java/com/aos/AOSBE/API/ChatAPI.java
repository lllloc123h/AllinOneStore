package com.aos.AOSBE.API;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.aos.AOSBE.DTOS.ChatMessage;

@Controller
public class ChatAPI {
	@MessageMapping("/private-message")
	@SendToUser("/queue/messages")
	public ChatMessage sendMessage(@Payload ChatMessage message, Principal user) {
		return message;
	}
}
