package com.aos.AOSBE.Service;

import com.aos.AOSBE.DTOS.ProvinceDTO;
import com.aos.AOSBE.DTOS.DistrictDTO;
import com.aos.AOSBE.DTOS.WardDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class GhnAddressService {

    @Value("${GHN_TOKEN}")
    private String ghnToken;

    @Value("${GHN_SHOPID}")
    private String ghnShopId;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Cache nội bộ để tránh gọi API lặp lại nếu Spring Cache chưa kích hoạt
    private final Map<Integer, List<DistrictDTO>> districtCache = new ConcurrentHashMap<>();
    private final Map<Integer, List<WardDTO>> wardCache = new ConcurrentHashMap<>();

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);
        headers.set("ShopId", ghnShopId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    // =================== PROVINCES ===================
    @Cacheable("ghnProvinces")
    public List<ProvinceDTO> getProvinces() {
        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/province";
        ResponseEntity<JsonNode> response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            new HttpEntity<>(createHeaders()),
            JsonNode.class
        );

        JsonNode data = response.getBody().get("data");
        return objectMapper.convertValue(data, new TypeReference<>() {});
    }

    public String getProvinceNameById(int provinceId) {
        return getProvinces().stream()
            .filter(p -> p.getProvinceID() == provinceId)
            .map(ProvinceDTO::getProvinceName)
            .findFirst()
            .orElse("Unknown");
    }
    
    public int getProvinceIdByName(String name) {
        String cleanedName = normalizeName(name);

        return getProvinces().stream()
            .filter(p -> normalizeName(p.getProvinceName()).equals(cleanedName))
            .map(ProvinceDTO::getProvinceID)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Province name not found: " + name));
    }
    
    private String normalizeName(String name) {
        return name
            .toLowerCase()
            .replace("tỉnh ", "")
            .replace("thành phố ", "")
            .replace("tp. ", "")
            .replaceAll("\\s+", " ")
            .trim();
    }


    // =================== DISTRICTS ===================
    @Cacheable(value = "ghnDistricts", key = "#provinceId")
    public List<DistrictDTO> getDistricts(int provinceId) {
        // Local cache trước khi gọi API
        if (districtCache.containsKey(provinceId)) {
            return districtCache.get(provinceId);
        }

        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/district";
        Map<String, Object> body = Map.of("province_id", provinceId);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(body, createHeaders()),
            JsonNode.class
        );

        JsonNode data = response.getBody().get("data");
        List<DistrictDTO> result = objectMapper.convertValue(data, new TypeReference<>() {});
        districtCache.put(provinceId, result); // cache nội bộ
        return result;
    }

    public String getDistrictNameById(int provinceId, int districtId) {
        return getDistricts(provinceId).stream()
            .filter(d -> d.getDistrictID() == districtId)
            .map(DistrictDTO::getDistrictName)
            .findFirst()
            .orElse("Unknown");
    }
    
    public int getDistrictIdByName(int provinceId, String name) {
        return getDistricts(provinceId).stream()
            .filter(d -> d.getDistrictName().equalsIgnoreCase(name.trim()))
            .map(DistrictDTO::getDistrictID)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("District name not found: " + name));
    }

    // =================== WARDS ===================
    @Cacheable(value = "ghnWards", key = "#districtId")
    public List<WardDTO> getWards(int districtId) {
        if (wardCache.containsKey(districtId)) {
            return wardCache.get(districtId);
        }

        String url = "https://online-gateway.ghn.vn/shiip/public-api/master-data/ward";
        Map<String, Object> body = Map.of("district_id", districtId);

        ResponseEntity<JsonNode> response = restTemplate.exchange(
            url,
            HttpMethod.POST,
            new HttpEntity<>(body, createHeaders()),
            JsonNode.class
        );

        JsonNode data = response.getBody().get("data");
        List<WardDTO> result = objectMapper.convertValue(data, new TypeReference<>() {});
        wardCache.put(districtId, result);
        return result;
    }

    public String getWardNameByCode(int districtId, String wardCode) {
        return getWards(districtId).stream()
            .filter(w -> w.getWardCode().equalsIgnoreCase(wardCode))
            .map(WardDTO::getWardName)
            .findFirst()
            .orElse("Unknown");
    }
    
    public String getWardCodeByName(int districtId, String name) {
        return getWards(districtId).stream()
            .filter(w -> w.getWardName().equalsIgnoreCase(name.trim()))
            .map(WardDTO::getWardCode)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Ward name not found: " + name));
    }

}
