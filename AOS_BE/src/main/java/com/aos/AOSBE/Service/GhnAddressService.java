package com.aos.AOSBE.Service;

import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GhnAddressService {

    @Value("${GHN_TOKEN}")
    private String ghnToken;

    @Value("${GHN_SHOPID}")
    private String ghnShopId;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private HttpHeaders createHeaders() {
        System.out.println("🧪 Token: " + ghnToken);
        System.out.println("🧪 ShopId: " + ghnShopId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken); // ✅ sửa đúng chữ T hoa
        headers.set("ShopId", ghnShopId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        System.out.println("🧪 Headers being sent: " + headers);
        return headers;
    }

    public List<Map<String, Object>> getProvinces() {
        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
        HttpEntity<?> entity = new HttpEntity<>(createHeaders()); // No body for GET
        ResponseEntity<JsonNode> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            entity,
            JsonNode.class
        );
        return extractDataList(response);
    }

    public List<Map<String, Object>> getDistricts(int provinceId) {
        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/district";
        Map<String, Object> body = Map.of("province_id", provinceId);
        HttpEntity<?> entity = new HttpEntity<>(body, createHeaders());

        ResponseEntity<JsonNode> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            JsonNode.class
        );

        return extractDataList(response);
    }

    public List<Map<String, Object>> getWards(int districtId) {
        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward";
        Map<String, Object> body = Map.of("district_id", districtId);
        HttpEntity<?> entity = new HttpEntity<>(body, createHeaders());

        ResponseEntity<JsonNode> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            entity,
            JsonNode.class
        );

        return extractDataList(response);
    }

    private List<Map<String, Object>> extractDataList(ResponseEntity<JsonNode> response) {
        JsonNode dataArray = response.getBody().get("data");
        List<Map<String, Object>> result = new ArrayList<>();
        if (dataArray != null && dataArray.isArray()) {
            for (JsonNode item : dataArray) {
                result.add(objectMapper.convertValue(item, Map.class));
            }
        }
        return result;
    }
}
