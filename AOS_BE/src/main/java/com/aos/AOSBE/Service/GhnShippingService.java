package com.aos.AOSBE.Service;

import com.aos.AOSBE.DTOS.GhnDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GhnShippingService {

    @Value("${GHN_TOKEN}")
    private String ghnToken;

    @Value("${GHN_SHOPID}")
    private String ghnShopId;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getShopAddressFromGHN() {
        String url = "https://online-gateway.ghn.vn/shiip/public-api/v2/shop/all";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> body = response.getBody();
            if (body != null && body.get("data") instanceof Map dataMap) {
                List<Map<String, Object>> shops = (List<Map<String, Object>>) dataMap.get("shops");
                if (!shops.isEmpty()) {
                    return shops.get(0); // shop đầu tiên
                }
            }
        }

        throw new RuntimeException("Không lấy được địa chỉ cửa hàng từ GHN");
    }

    public Map<String, Object> calculateShippingFee(int serviceId, int toDistrictId, String toWardCode) {
        Map<String, Object> shop = getShopAddressFromGHN();

        Integer fromDistrictId = (Integer) shop.get("district_id");
        String fromWardCode = (String) shop.get("ward_code");

        String url = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);
        headers.set("ShopId", ghnShopId);

        Map<String, Object> body = new HashMap<>();
        body.put("service_id", serviceId);
        body.put("insurance_value", 100000); // Tổng tiền đơn hàng
        body.put("from_district_id", fromDistrictId);
        body.put("from_ward_code", fromWardCode);
        body.put("to_district_id", toDistrictId);
        body.put("to_ward_code", toWardCode);
        body.put("weight", 500);
        body.put("length", 20);
        body.put("width", 20);
        body.put("height", 10);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            return (Map<String, Object>) response.getBody().get("data");
        }

        throw new RuntimeException("Không tính được phí vận chuyển");
    }
}
