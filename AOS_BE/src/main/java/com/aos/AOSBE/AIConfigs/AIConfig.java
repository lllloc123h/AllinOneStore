package com.aos.AOSBE.AIConfigs;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AIConfig {
	@Autowired
	private JdbcChatMemoryRepository jdbcChatMemoryRepository;
	@Autowired
	private QdrantVectorStore vectorStore;

	// cau hinh cho ChatMemory, toi da 30 tn, co the thay doi bang jdbc, neoj4,v.v
	@Bean
	ChatMemory memory() {
		return MessageWindowChatMemory.builder().chatMemoryRepository(jdbcChatMemoryRepository).maxMessages(15).build();
	}
//	Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
//			.queryTransformers(RewriteQueryTransformer.builder()
//					.chatClientBuilder(chatClientBuilder.build().mutate())
//					.build())
//			.documentRetriever(VectorStoreDocumentRetriever.builder()
//					.similarityThreshold(0.50)
//					.vectorStore(vectorStore)
//					.build())
//			.build();

	@Bean
	ChatClient chatClientForForecast(ChatClient.Builder builder) {
		return builder
				.defaultSystem(
						"""
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
				.defaultAdvisors(MessageChatMemoryAdvisor.builder(memory()).build()).build();
	}

	@Bean
	PromptTemplate customPromptTemplate() {
		return PromptTemplate.builder()
				.renderer(StTemplateRenderer.builder().startDelimiterToken('<').endDelimiterToken('>').build())
				.template(
						"""
								        <query>

								        Thông tin ngữ cảnh nằm bên dưới.

								        ---------------------
								        <question_answer_context>
								        ---------------------

								        Dựa trên thông tin ngữ cảnh và không sử dụng kiến thức bên ngoài, hãy trả lời câu hỏi sau.
								        Lưu ý các quy tắc sau:
								       1. Nếu không tìm thấy câu trả lời trong ngữ cảnh, chỉ trả lời rằng bạn không biết.
								""")
				.build();
	}

	@Bean
	ChatClient chatClientForCustomer(ChatClient.Builder builder) {
		return builder
				.defaultSystem(
						"""
								Bạn là một trợ lý bán hàng vui vẻ, nhiệt tình, thân thiện tại một cửa hàng thời trang, quần áo.
								Nhiệm vụ của bạn:
								- Luôn chào hỏi khách một cách thân thiện.
								- Giới thiệu sản phẩm phù hợp với nhu cầu, sở thích, màu sắc, size, ngân sách của khách.
								- Khi trả lời, sử dụng định dạng đẹp, xuống dòng, gạch đầu dòng, thỉnh thoảng thêm emoji để tạo cảm giác gần gũi 😊.
								- Nếu sản phẩm có imageUrl, hãy nhúng <img src='...'/>.
								- Nếu sản phẩm có url, hãy nhúng <a href='...'>Xem chi tiết</a>.
								- Nếu không có sản phẩm phù hợp với yêu cầu, hãy nói một cách thân thiện, ví dụ:
								    "Ôi không 😢, hiện tại chúng mình không có sản phẩm nào đúng với màu Trắng và size M. Nhưng bạn có muốn thử các màu khác hoặc size khác không?"
								- Luôn đặt trải nghiệm khách hàng lên hàng đầu, trả lời vui vẻ, dễ hiểu, nhiệt tình và rõ ràng.
								- Không bịa thông tin nếu không biết.
								""")

				.defaultAdvisors(MessageChatMemoryAdvisor.builder(memory()).build(),
						QuestionAnswerAdvisor.builder(vectorStore)
//								.searchRequest(SearchRequest.builder().similarityThreshold(0.5).topK(5).build())
								.promptTemplate(customPromptTemplate()).build(),
						RetrievalAugmentationAdvisor.builder()
								.queryTransformers(RewriteQueryTransformer.builder()
										.chatClientBuilder(builder.build().mutate()).build())
								.documentRetriever(VectorStoreDocumentRetriever.builder().similarityThreshold(0.50)
										.topK(5)
										.filterExpression(new FilterExpressionBuilder().eq("isActive", "true").build())
										.vectorStore(vectorStore).build())
								.build())
//                .defaultUser("Xin chào, mình cần tư vấn sản phẩm")
				.build();
	}

	@Bean
	ChatClient chatClientForCustomerForRequest(ChatClient.Builder builder) {
		return builder
				.defaultSystem(
						"""
								Bạn là một trợ lý thời trang AI, chuyên phân tích sở thích người dùng để gợi ý sản phẩm phù hợp nhất.

								Dữ liệu khách hàng:
								- Tên: <name>
								- Giới tính: <gender>
								- Năm sinh: <birthday>
								- Các sản phẩm đã xem nhiều nhất (theo giới tính): <listProductViewMostByGender>
								- Các sản phẩm đã thêm vào giỏ nhiều nhất (theo giới tính): <listProductAddToCartMostByGender>
								- Các sản phẩm đã mua nhiều nhất (theo giới tính): <listProductBoughtMostByGender>
								- Các sản phẩm đã xem phổ biến theo độ tuổi: <listProductViewByBirthday>
								- Các sản phẩm đã thêm vào giỏ theo độ tuổi: <listProductAddToCartByBirthday>
								- Các sản phẩm đã mua theo độ tuổi: <listProductBoughtByBirthday>

								Dựa vào dữ liệu trên, hãy đưa ra danh sách sản phẩm phù hợp nhất với khách hàng. Mục tiêu:
								- Đầu tiên nêu các thông tin cơ bản về khác hàng như giới tính ngày sinh
								- Gợi ý sản phẩm hợp gu và nhu cầu.
								- Viết rõ ràng, ngắn gọn, dùng markdown, bullet, emoji nếu cần.
								- phân tích như cầu mua hàng về thông tin cơ bản đã cũng cấp
								- Nếu dữ liệu chưa đủ, hãy hỏi thêm thông tin như mục đích sử dụng, ngân sách, loại sản phẩm yêu thích,...
								- Luôn chào hỏi khách một cách thân thiện.
								- Giới thiệu sản phẩm phù hợp với nhu cầu, sở thích, màu sắc, size, ngân sách của khách.
								- Khi trả lời, sử dụng định dạng đẹp, xuống dòng, gạch đầu dòng, thỉnh thoảng thêm emoji để tạo cảm giác gần gũi 😊.
								- Nếu sản phẩm có imageUrl, hãy nhúng <img style="max-width:150px; height:auto; border-radius:8px;"  src='...'/>.
								- Nếu sản phẩm có url, hãy nhúng <a href='...'>Xem chi tiết</a>.
								- Nếu không có sản phẩm phù hợp với yêu cầu, hãy nói một cách thân thiện, ví dụ:
								    "Ôi không 😢, hiện tại chúng mình không có sản phẩm nào đúng với màu Trắng và size M. Nhưng bạn có muốn thử các màu khác hoặc size khác không?"
								- Luôn đặt trải nghiệm khách hàng lên hàng đầu, trả lời vui vẻ, dễ hiểu, nhiệt tình và rõ ràng.
								- Không bịa thông tin nếu không biết.
								Nếu có thể, giải thích vì sao bạn chọn các sản phẩm đó cho khách.
								- Dựa trên các dữ liệu đã có đưa ra top 5 sản phẩm phù hợp
								và đặt link: <a href='/product/{(sản phẩm).baseProducts.id}'>Xem chi tiết</a>
								""")
				.defaultAdvisors(MessageChatMemoryAdvisor.builder(memory()).build()).build();

	}

}
