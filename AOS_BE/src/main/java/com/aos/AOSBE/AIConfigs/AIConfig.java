package com.aos.AOSBE.AIConfigs;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;

import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.prompt.PromptTemplate;

import org.springframework.ai.document.Document;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
 class AIConfig {
@Autowired
    private JdbcChatMemoryRepository jdbcChatMemoryRepository;
@Autowired
        private QdrantVectorStore vectorStore;
    // cau hinh cho ChatMemory, toi da 30 tn, co the thay doi bang jdbc, neoj4,v.v
    @Bean
    ChatMemory memory (){
        return  MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .maxMessages(25)
                .build();
    }

    @Bean
    PromptTemplate customPromptTemplate () {
       return PromptTemplate.builder()
                .renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
                .template("""
                                <query>
                        
                                Thông tin ngữ cảnh nằm bên dưới.
                        
                                ---------------------
                                <question_answer_context>
                                ---------------------
                        
                                Dựa trên thông tin ngữ cảnh và không sử dụng kiến thức bên ngoài, hãy trả lời câu hỏi sau.
                        
                                Lưu ý các quy tắc sau:
                        
                                1. Nếu không tìm thấy câu trả lời trong ngữ cảnh, chỉ cần trả lời rằng bạn không biết.
                                2. Tránh các câu kiểu như "Dựa trên ngữ cảnh..." hoặc "Theo thông tin cung cấp...".
                        """)
                .build();
    }
    //chiến lược gửi request, ở đây là gửi tối đa 8000 token, với 10% dự trữ
//    @PostConstruct
//    public void loadSampleData() {
//        List<Document> docs = List.of(
//                new Document("Áo thun nam chất liệu cotton, thoáng mát, phù hợp mùa hè.",
//                        Map.of("category", "áo thun", "gender", "nam")),
//                new Document("Váy nữ dáng dài, màu pastel, thích hợp đi tiệc.",
//                        Map.of("category", "váy", "gender", "nữ")),
//                new Document("Quần jeans nam co giãn, dễ phối đồ.",
//                        Map.of("category", "quần jeans", "gender", "nam"))
//        );
//        vectorStore.accept(docs);
//    }
    @Bean
    ChatClient chatClient(ChatClient.Builder builder) {
        return builder.defaultSystem("""
                Bạn là một trợ lý bán hàng chuyên nghiệp và thân thiện tại một cửa hàng chuyên về thời trang, quần áo.
                Nhiệm vụ của bạn:
                - Giới thiệu, tư vấn sản phẩm phù hợp với nhu cầu và sở thích của khách hàng.
                - Trả lời có định dạng đẹp, sử dụng xuống dòng, gạch đầu dòng, emoji nếu cần.
                - Trả lời các câu hỏi về đặc điểm, chất liệu, công dụng, giá cả, ưu đãi, chính sách đổi trả.
                - Đưa ra gợi ý dựa trên thông tin khách hàng cung cấp (giới tính, độ tuổi, mục đích sử dụng, ngân sách, v.v.).
                - Luôn trả lời ngắn gọn, dễ hiểu, nhiệt tình và rõ ràng.
                - Không bịa ra thông tin nếu không biết — chỉ trả lời dựa trên dữ liệu được cung cấp.
                - Nếu khách hỏi biết Trần Hữu Lộc hay không, hãy nói rằng "anh ấy rất đẹp trai".
                Nếu không có đủ thông tin, hãy chủ động hỏi lại khách hàng để làm rõ nhu cầu.
                
                Luôn ưu tiên giúp khách hàng chọn được sản phẩm phù hợp và hài lòng nhất.
                
                """).
                defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(memory()).build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                        .searchRequest(SearchRequest.builder()
                                        .similarityThreshold(0.5)
                                        .topK(5)
                                        .build())
                        .promptTemplate(customPromptTemplate()).build()
                                ).
                defaultUser("Xin chào, mình cần tư vấn sản phẩm")
                .build();
    }





//    PromptTemplate customTemplate = new SystemPromptTemplate(
//            """
//   {instructions}
//
//   Các thông tin có thể hữu ích được tìm thấy trong cơ sở tri thức:
//
//   {long_term_memory}
//   """
//    );
//    PromptChatMemoryAdvisor advisor = PromptChatMemoryAdvisor.builder(memory)
//            .systemPromptTemplate(customTemplate)
//            .build();

//VectorStoreChatMemoryAdvisor advisor = VectorStoreChatMemoryAdvisor.builder(vectorStore)
//        .promptTemplate(customTemplate)
//        .build();

}
