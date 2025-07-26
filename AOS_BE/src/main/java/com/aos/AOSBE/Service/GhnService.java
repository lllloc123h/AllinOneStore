package com.aos.AOSBE.Service;

import com.aos.AOSBE.DTOS.GhnDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GhnService {

    private final String ghnToken = System.getProperty("GHN_TOKEN");
    private final String shopId = System.getProperty("GHN_SHOPID");


    private final String GHN_URL = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail";

    public GhnDTO getOrderDetailFromGHN(String orderCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);

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
}
