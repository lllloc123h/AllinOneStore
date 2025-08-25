package com.aos.AOSBE.Service;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Repository.ProductImagesRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.aos.AOSBE.AIConfigs.AITools;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {
	private final ChatClient chatClientForCustomer;
	private final ChatClient chatClientForForecast;
	private final ChatClient chatClientForCustomerForRequest;

	@Autowired
	private ProductItemsService productItemsService;
	@Autowired
	private AITools aiTools;


	public OpenAIService(@Qualifier("chatClientForCustomer") ChatClient chatClientForCustomer,
			@Qualifier("chatClientForForecast") ChatClient chatClientForForecast,
			@Qualifier("chatClientForCustomerForRequest") ChatClient chatClientForCustomerForRequest) {
		this.chatClientForCustomer = chatClientForCustomer;
		this.chatClientForForecast = chatClientForForecast;
		this.chatClientForCustomerForRequest = chatClientForCustomerForRequest;
	}

	public String userChatBot(String message, String conversationId) {
		FilterExpressionBuilder b = new FilterExpressionBuilder();

		// dinh dang response dep hon
		String resp = this.chatClientForCustomer.prompt().user(message)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
//				.advisors(a -> a.param(VectorStoreDocumentRetriever.FILTER_EXPRESSION, b.eq("isActive", true)))
//				.advisors(a -> a.param(VectorStoreDocumentRetriever.FILTER_EXPRESSION, "isActive == true"))
				.call().content();
		return resp;
	}

	public String adminChatBot(String message, String conversationId) {
		// dinh dang response dep hon
		String resp = this.chatClientForForecast.prompt().user(message)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId)).call().content();
		return resp;
	}

	public String forecastChatBot(String message, String conversationId) {
		ToolCallback[] foreTools = ToolCallbacks.from(aiTools);
		String resp = this.chatClientForForecast.prompt().toolCallbacks(foreTools).user(message)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId)).call().content();
		return resp;
	}

	public String personalProductChatBot(String message, String conversationId) {
		ToolCallback[] foreTools = ToolCallbacks.from(aiTools);
		String resp = this.chatClientForCustomerForRequest.prompt().toolCallbacks(foreTools).user(message)
				.advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId)).call().content();
		return resp;
	}


//        sử dụng khi bấm nút muốn làm chức năng gì đó, chỉ gán vào 1 lần request này
//        ToolCallback[] dateTimeTools = ToolCallbacks.from(new DateTimeTools());
//ChatOptions chatOptions = ToolCallingChatOptions.builder()
//    .toolCallbacks(dateTimeTools)
//    .build();
//Prompt prompt = new Prompt("What day is tomorrow?", chatOptions);
//chatModel.call(prompt);

}
