package com.aos.AOSBE.Service;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Repository.ProductImagesRepository;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.Collections;
import io.qdrant.client.grpc.Points;
import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.Filter;
import org.springframework.ai.vectorstore.qdrant.QdrantVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static io.qdrant.client.ValueFactory.value;

@Service
public class QdrantService {

    @Autowired
    private HandleListSkuToFilter handleListSkuToFilter;
    @Autowired
    private ProductImagesRepository productImagesRepository;
    @Value("${QDRANT_KEY}")
    private String qdrantKey;
    @Value("${QDRANT_HOST}")
    private String qdrantHost;
    @Autowired
    private QdrantVectorStore qdrantStore;

@Autowired
QdrantClient qdrantClient;
    @Transactional
    public List<Document> createDocumentForChatBotSearch(ProductItems productItems) {
        String docContent = "Tên sản phẩm: "+productItems.getBaseProducts().getName() + " với mô tả "
                + productItems.getDescription() + ". Thông tin màu sắc, size: "
                + handleListSkuToFilter.getDescriptionOfSku(productItems.getSku()) + ". Tên danh mục: "
                + productItems.getBaseProducts().getCategories().getName();
        // 3. Thêm vào Qdrant
        Map<String, Object> payload = new HashMap<>();
//		payload.put("createdAt", productItems.getCreatedAt().toString());
//		payload.put("updatedAt", productItems.getUpdatedAt().toString());
        payload.put("productItemId", productItems.getId());
        payload.put("baseProductId", productItems.getBaseProducts().getId());
        payload.put("category", productItems.getBaseProducts().getCategories().getName());
        payload.put("catalog", productItems.getBaseProducts().getCategories().getCatalogs().getName());
        payload.put("isCustom", productItems.getBaseProducts().isCustom());
        payload.put("isActive", productItems.isActive() ? "true" : "false");
        payload.put("url","http://localhost:5173/product/" + productItems.getBaseProducts().getId());
        payload.put("color", handleListSkuToFilter.getSingleDescriptionColorOfSku(productItems.getSku()));
        payload.put("size", handleListSkuToFilter.getSingleDescriptionSizeOfSku(productItems.getSku()));
        payload.put("imageUrl",productImagesRepository.checkContainDefaultImagesByProductItemId(productItems.getId()).get(0).getImageUrl());
        Document doc = new Document(
                productItems.getSearchPoint().toString(),
                docContent,
                payload);
        qdrantStore.doAdd(List.of(doc));
        return List.of(doc);
    }
    @Transactional
    public void upsertDocument(ProductItems productItems) {
        createDocumentForChatBotSearch(productItems);
    }
    @Transactional
    public void deleteDocumentByPayloadProductItemId(int productItemId) {
        Filter.Expression filterExpression = new Filter.Expression(
                Filter.ExpressionType.EQ,
                new Filter.Key("productItemId"),
                new Filter.Value(productItemId)
        );
        qdrantStore.delete(filterExpression);
    }
    public void setPayLoad(String imageUrl, String searchPoint) throws Exception {
        qdrantClient
                .setPayloadAsync(
                        "vector_store",
                        Map.of("imageUrl", value(imageUrl)),
                        Points.PointId.newBuilder().setUuid(searchPoint).build(),
                        true,
                        null,
                        null)
                .get();
    }


    // tạo index
    @PostConstruct
    public void init() {
        createIndexIfNotExists("vector_store", "productItemId", Collections.PayloadSchemaType.Integer);
        createIndexIfNotExists("vector_store", "size", Collections.PayloadSchemaType.Keyword);
        createIndexIfNotExists("vector_store", "color", Collections.PayloadSchemaType.Keyword);
        createIndexIfNotExists("vector_store", "isActive", Collections.PayloadSchemaType.Keyword);
    }

    private void createIndexIfNotExists(String collection, String field, Collections.PayloadSchemaType type) {
        try {
            Points.UpdateResult result = qdrantClient.createPayloadIndexAsync(
                    collection,
                    field,
                    type,
                    null,
                    true,
                    null,
                    null
            ).get();

            System.out.printf("Index '%s' created successfully in '%s'%n", field, collection);

        } catch (Exception e) {
            String msg = e.getMessage();
            if (msg != null && msg.contains("already exists")) {
                System.out.printf("Index '%s' already exists in '%s', skipping...%n", field, collection);
            } else {
                System.err.printf("Failed to create index '%s' in '%s': %s%n", field, collection, e.getMessage());
            }
        }
    }


}
