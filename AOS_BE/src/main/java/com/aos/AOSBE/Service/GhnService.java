package com.aos.AOSBE.Service;

import com.aos.AOSBE.DTOS.GhnCreateOrderRequestDTO;
import com.aos.AOSBE.DTOS.GhnDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GhnService {

    private final String ghnToken = System.getProperty("GHN_TOKEN");
    private final String ghnShopId = System.getProperty("GHN_SHOPID");


    private final String GHN_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail";

    public GhnDTO getOrderDetailFromGHN(String orderCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);
        headers.set("ShopId", ghnShopId);

        Map<String, String> body = Map.of("order_code", orderCode);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<GhnResponseWrapper> response = restTemplate.exchange(
                GHN_URL, HttpMethod.POST, request, GhnResponseWrapper.class
        );

        return response.getBody().getData(); // trả về GhnDTO chứa trạng thái
    }

    // Lớp wrapper nếu GHN trả về { "code":200, "data":{...} }
    public static class GhnResponseWrapper {
        private int code;
        private GhnDTO data;
        public GhnDTO getData() { return data; }
        public void setData(GhnDTO data) { this.data = data; }
    }

    private static final String GHN_CREATE_ORDER_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/create";

    public String createGhnOrderCode() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);
        headers.set("ShopId", ghnShopId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        GhnCreateOrderRequestDTO dto = new GhnCreateOrderRequestDTO();
        dto.setPayment_type_id(2);
        dto.setNote("Tintest 123");
        dto.setRequired_note("KHONGCHOXEMHANG");
        dto.setReturn_phone("0332190158");
        dto.setReturn_address("39 NTT");
        dto.setReturn_district_id(1450);
        dto.setReturn_ward_code("20308");
        dto.setClient_order_code(UUID.randomUUID().toString());
        dto.setFrom_name("TinTest124");
        dto.setFrom_phone("0987654321");
        dto.setFrom_address("72 Thành Thái");
        dto.setFrom_ward_name("Phường 14");
        dto.setFrom_district_name("Quận 10");
        dto.setFrom_province_name("Hồ Chí Minh");
        dto.setTo_name("TinTest124");
        dto.setTo_phone("0987654321");
        dto.setTo_address("72 Thành Thái");
        dto.setTo_ward_name("Phường 14");
        dto.setTo_district_name("Quận 10");
        dto.setTo_province_name("Hồ Chí Minh");
        dto.setCod_amount(200000);
        dto.setContent("Theo New York Times");
        dto.setLength(12);
        dto.setWidth(12);
        dto.setHeight(12);
        dto.setWeight(1200);
        dto.setCod_failed_amount(2000);
        dto.setPick_station_id(1444);
        dto.setInsurance_value(1000000);
        dto.setService_type_id(2);
        dto.setPickup_time(System.currentTimeMillis() / 1000);
        dto.setPick_shift(List.of(2));

        GhnCreateOrderRequestDTO.Item item = new GhnCreateOrderRequestDTO.Item();
        item.setName("Áo Polo");
        item.setCode("Polo123");
        item.setQuantity(1);
        item.setPrice(200000);
        item.setLength(12);
        item.setWidth(12);
        item.setHeight(12);
        item.setWeight(1200);

        GhnCreateOrderRequestDTO.Category category = new GhnCreateOrderRequestDTO.Category();
        category.setLevel1("Áo");
        item.setCategory(category);

        dto.setItems(List.of(item));

        HttpEntity<GhnCreateOrderRequestDTO> request = new HttpEntity<>(dto, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(GHN_CREATE_ORDER_URL, request, Map.class);

        if (response.getBody() != null && ((Integer) response.getBody().get("code")) == 200) {
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            return data != null ? (String) data.get("order_code") : null;
        } else {
            throw new RuntimeException("GHN order creation failed: " + response.getBody().get("message"));
        }

    }
}
