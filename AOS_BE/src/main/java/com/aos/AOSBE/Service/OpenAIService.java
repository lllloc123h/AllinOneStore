package com.aos.AOSBE.Service;

import com.aos.AOSBE.AIConfigs.AITools;
import com.aos.AOSBE.DTOS.ProductItemsDTOS;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.stereotype.Service;

@Service
public class OpenAIService {
	private final ChatClient chatClient;

	public OpenAIService(ChatClient chatClient) {
		this.chatClient = chatClient;
	}

	public String normalChatBot(String message, String conversationId) {
		// dinh dang response dep hon
		String resp = this.chatClient.prompt().user(message)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId)).call().content();
		return resp;
	}
	public String forecastChatBot(String message, String conversationId,Integer productItemId) {
		// dinh dang response dep hon
		ToolCallback[] forecastTool = ToolCallbacks.from(new AITools().analyzeProductTrend(new ProductItemsDTOS()));
		String resp = this.chatClient.prompt().user(message)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
				.toolCallbacks(forecastTool).call().content();
		return resp;
	}
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

}
