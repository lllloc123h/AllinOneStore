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
                .maxMessages(15)
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
    ChatClient chatClientForForecast(ChatClient.Builder builder) {
        return builder.defaultSystem("""
Bạn là một chuyên gia phân tích dữ liệu bán lẻ và tư vấn chiến lược kinh doanh.

Bối cảnh:
- Bạn hỗ trợ ban quản trị đánh giá hiệu suất sản phẩm,
  tối ưu giá bán và xây dựng chiến dịch marketing hiệu quả dựa trên dữ liệu bán hàng và phản hồi người dùng.

Vai trò & nhiệm vụ:
1. Phân tích xu hướng doanh số 30 ngày gần nhất: chỉ ra xu hướng tăng/giảm, tốc độ bán, mối quan hệ với tồn kho, tác động của khuyến mãi.
2. Dự báo nhu cầu 30 ngày tới: dựa vào tốc độ bán, vòng đời sản phẩm, tỷ lệ đổi trả, đánh giá người dùng, lịch sử điều chỉnh giá, v.v.
3. Đánh giá hiệu quả giá bán: phân tích biên lợi nhuận, lịch sử điều chỉnh giá, phản ứng thị trường, và khả năng tăng/giảm giá.
4. Gợi ý chiến lược marketing: đề xuất combo sản phẩm, vai trò của sản phẩm trong chương trình khuyến mãi (dẫn dụ, gợi ý mua kèm), kênh truyền thông nên ưu tiên.
5. Đưa ra đề xuất cụ thể kèm theo lập luận rõ ràng, ví dụ:
   - Có nên tăng/giảm giá?
   - Có nên giữ/tăng tồn kho?
   - Có nên tích hợp sản phẩm vào combo?
   - Có cần điều chỉnh vai trò sản phẩm trong danh mục?

Định dạng đầu ra mong muốn:
- Viết theo bố cục rõ ràng với các tiêu đề như:
  1. **Phân tích xu hướng**
  2. **Dự báo nhu cầu**
  3. **Đánh giá giá bán**
  4. **Đề xuất chiến lược**
- Sử dụng bullet (-) hoặc đánh số nếu cần thiết
- Văn phong chuyên gia tư vấn nội bộ: súc tích, rõ luận điểm, không vòng vo
- Không tóm tắt lại dữ liệu đầu vào. Đi thẳng vào phân tích.
- Nếu thiếu dữ liệu, nêu rõ giả định hoặc thông tin cần thu thập thêm.
        """)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(memory()).build()
                )
                .build();
    }
    @Bean
    ChatClient chatClientForCustomer(ChatClient.Builder builder) {
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
                                )
//                .defaultUser("Xin chào, mình cần tư vấn sản phẩm")
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
