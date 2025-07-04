package com.aos.AOSBE.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
	private final ChatClient chatClient;

	public OpenAIService(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	public String chatWithGPT(String message) {
		// dinh dang response dep hon
		String resp = this.chatClient.prompt().user(message)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, "001")).call().content();
//        dùng khi muốn truy xuất trò truyện từ trước của ng dùng đã ĐĂNG NHẬP
//        String conversationId = "007";
//chatClient.prompt()
//    .user("Do I have license to code?")
//    .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
//    .call()
//    .content();

//        sử dụng khi bấm nút muốn làm chức năng gì đó, chỉ gán vào 1 lần request này
//        ToolCallback[] dateTimeTools = ToolCallbacks.from(new DateTimeTools());
//ChatOptions chatOptions = ToolCallingChatOptions.builder()
//    .toolCallbacks(dateTimeTools)
//    .build();
//Prompt prompt = new Prompt("What day is tomorrow?", chatOptions);
//chatModel.call(prompt);

		return resp;
	}

//    Map<String, Object> result = ChatClient.create(chatModel).prompt()
//        .user(u -> u.text("Provide me a List of {subject}")
//                    .param("subject", "an array of numbers from 1 to 9 under they key name 'numbers'"))
//        .call()
//        .entity(new ParameterizedTypeReference<Map<String, Object>>() {});
}
