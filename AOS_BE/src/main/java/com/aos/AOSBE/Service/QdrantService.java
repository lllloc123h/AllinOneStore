package com.aos.AOSBE.Service;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Repository.ProductImagesRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
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

@Service
public class QdrantService {
    @Autowired
    private VectorStore vectorStore;
    @Autowired
    private HandleListSkuToFilter handleListSkuToFilter;
    @Autowired
    private ProductImagesRepository productImagesRepository;
    @Transactional
    public void createDocument(ProductItems productItems) {
        String docContent = "Tên sản phẩm: "+productItems.getBaseProducts().getName() + " với mô tả "
                + productItems.getDescription() + ". Thông tin màu sắc, size: "
                + handleListSkuToFilter.getDescriptionOfSku(productItems.getSku()) + ". Tên danh mục: "
                + productItems.getBaseProducts().getCategories().getName();
        // 3. Thêm vào Qdrant
        Map<String, Object> payload = new HashMap<>();

//		payload.put("createdAt", productItems.getCreatedAt().toString());
//		payload.put("updatedAt", productItems.getUpdatedAt().toString());
        payload.put("baseProductId", productItems.getBaseProducts().getId());
        payload.put("category", productItems.getBaseProducts().getCategories().getName());
        payload.put("catalog", productItems.getBaseProducts().getCategories().getCatalogs().getName());
        payload.put("isCustom", productItems.getBaseProducts().isCustom());
        payload.put("isActive", productItems.isActive());
        payload.put("url","http://localhost:5173/product/" + productItems.getBaseProducts().getId());
        payload.put("color", handleListSkuToFilter.getSingleDescriptionColorOfSku(productItems.getSku()));
        payload.put("size", handleListSkuToFilter.getSingleDescriptionSizeOfSku(productItems.getSku()));

        payload.put("imageUrl",productImagesRepository.checkContainDefaultImagesByProductItemId(productItems.getId()).get(0).getImageUrl());
        Document doc = new Document(
                String.valueOf(productItems.getId()),
                docContent,
                payload);
        vectorStore.add(List.of(doc));
    }
    @Transactional
    public void deleteDocument(ProductItems productItems) {
        // 3. Thêm vào Qdrant
        vectorStore.delete(List.of(String.valueOf(productItems.getId())));
    }
    @Transactional
    public void updateProductPayload(Long productItemId, Map<String, Object> updatedPayload) {
        String qdrantUrl = "http://localhost:6333/collections/vector_store/points/payload";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("points", new String[]{String.valueOf(productItemId)});
        requestBody.put("payload", updatedPayload);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(qdrantUrl, requestEntity, String.class);

        System.out.println("Qdrant update response: " + response.getBody());
    }
}
