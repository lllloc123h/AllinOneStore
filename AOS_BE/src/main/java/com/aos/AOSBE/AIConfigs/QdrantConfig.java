//package com.aos.AOSBE.AIConfigs;
//
//import com.knuddels.jtokkit.api.EncodingType;
//import io.qdrant.client.QdrantClient;
//import io.qdrant.client.QdrantGrpcClient;
//import org.springframework.ai.embedding.BatchingStrategy;
//import org.springframework.ai.embedding.EmbeddingModel;
//import org.springframework.ai.embedding.TokenCountBatchingStrategy;
//import org.springframework.ai.openai.OpenAiEmbeddingModel;
//import org.springframework.ai.openai.api.OpenAiApi;
//import org.springframework.ai.vectorstore.VectorStore;
//import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//import java.util.List;
//@Configuration
//public class QdrantConfig {
//    @Bean
//    public BatchingStrategy customTokenCountBatchingStrategy() {
//        return new TokenCountBatchingStrategy(
//                EncodingType.CL100K_BASE,  // Specify the encoding type
//                8000,                      // Set the maximum input token count
//                0.1                        // Set the reserve percentage
//        );
//    }
//    @Bean
//    public QdrantClient qdrantClient() {
//        QdrantGrpcClient.Builder grpcClientBuilder =
//                QdrantGrpcClient.newBuilder(
//                        "588306d2-73e6-467e-bcda-6c6f3ce36dec.us-east4-0.gcp.cloud.qdrant.io",
//                        6334,
//            true);
//        grpcClientBuilder.withApiKey("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2Nlc3MiOiJtIn0.brkuYvFwVRg0Cqw7Mov0KmsZWsAW98S38sgi5QoPiNA");
//        return new QdrantClient(grpcClientBuilder.build());
//    }
//    @Bean
//    public VectorStore vectorStore(QdrantClient qdrantClient, EmbeddingModel embeddingModel) {
//        return QdrantVectorStore.builder(qdrantClient, embeddingModel)
//                .collectionName("data-test")     // Optional: defaults to "vector_store"
////                .initializeSchema(true)                  // Optional: defaults to false
//                .batchingStrategy(customTokenCountBatchingStrategy()) // Optional: defaults to TokenCountBatchingStrategy
//                .build();
//    }
//
//
////    // This can be any EmbeddingModel implementation
//    @Bean
//    public EmbeddingModel embeddingModel() {
//        String apiKey = System.getProperty("OPENAI_API_KEY");
//        return new OpenAiEmbeddingModel(new OpenAiApi(apiKey));
//    }
////    List<String> collections = client.listCollectionsAsync(Duration.ofSeconds(5)).get();
//
//}
//
