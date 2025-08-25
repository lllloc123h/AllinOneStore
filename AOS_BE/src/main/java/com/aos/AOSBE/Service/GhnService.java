package com.aos.AOSBE.Service;

import com.aos.AOSBE.DTOS.GhnCreateOrderRequestDTO;
import com.aos.AOSBE.DTOS.GhnDTO;
import com.aos.AOSBE.Entity.OrderItems;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Entity.ProductItems;

import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class GhnService {

    @Autowired
    private GhnShippingService ghnShippingService;
    @Autowired
    private OrderItemsService orderItemsService;


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

    public GhnCreateOrderRequestDTO buildGhnDTOFromOrder(Orders order, String requiredNote) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);
        headers.set("ShopId", ghnShopId);

        List<GhnCreateOrderRequestDTO.GhnItemDTO> itemDTOs = new ArrayList<>();
        int totalWeight = 0;
        List<OrderItems> listOrderItemToCreateGHN = orderItemsService.findByOrderId(order.getId());
        for (OrderItems item : listOrderItemToCreateGHN) {
            ProductItems productItem = item.getProductItems();
            var baseProduct = productItem.getBaseProducts();

            GhnCreateOrderRequestDTO.GhnItemDTO dtoItem = new GhnCreateOrderRequestDTO.GhnItemDTO();
            dtoItem.setName(baseProduct.getName());
            dtoItem.setCode(productItem.getSku());
            dtoItem.setQuantity(item.getQty());
            dtoItem.setPrice((int) item.getSellingPrice()); // ép kiểu double -> int
            dtoItem.setLength(12);
            dtoItem.setWidth(12);
            dtoItem.setHeight(12);
            dtoItem.setWeight(1200);

            // Gán category
            GhnCreateOrderRequestDTO.GhnItemDTO.GhnCategory category = new GhnCreateOrderRequestDTO.GhnItemDTO.GhnCategory();
            category.setLevel1(baseProduct.getCategories().getName());
            dtoItem.setCategory(category);

            totalWeight += dtoItem.getWeight() * dtoItem.getQuantity();
            itemDTOs.add(dtoItem);
        }

        var account = order.getAccounts();
        // var address = order.getAccounts().getUserAddresses();

        GhnCreateOrderRequestDTO dto = new GhnCreateOrderRequestDTO();

        // 1. Thông tin thanh toán & ghi chú
        if (order.getFreeshipCouponCode() != null && !order.getFreeshipCouponCode().isBlank()) {
            dto.setPayment_type_id(1); // Bên gửi trả phí
        } else {
            dto.setPayment_type_id(2); // Bên nhận trả phí
        }
        dto.setRequired_note(requiredNote);

        // 2. Thông tin trả hàng
        dto.setReturn_phone("0332190158");
        dto.setReturn_address("39 NTT");
        dto.setReturn_district_id(1454);
        dto.setReturn_ward_code("21204");

        // 3. Mã đơn hàng khách (client)
        dto.setClient_order_code(UUID.randomUUID().toString());

        // 4. Người gửi
        Map<String, Object> shop = ghnShippingService.getShopAddressFromGHN();

        dto.setFrom_name((String) shop.get("name"));
        dto.setFrom_phone((String) shop.get("phone"));
        dto.setFrom_address((String) shop.get("address"));
        dto.setFrom_ward_name((String) shop.get("ward_name"));
        dto.setFrom_district_name((String) shop.get("district_name"));
        dto.setFrom_province_name((String) shop.get("province_name"));
        dto.setNote(order.getNote());
        // 5. Người nhận

        // dto.setTo_name(account.getFullname());
        // dto.setTo_phone(account.getPhone());
        // dto.setTo_address("72 Thành Thái, Phường 14, Quận 10, Hồ Chí Minh, Vietnam");
        // dto.setTo_ward_name("Phường 14");
        // dto.setTo_district_name("Quận 10");
        // dto.setTo_province_name("HCM");

        String orderInfor = order.getOrderInfor();

        if (orderInfor != null && !orderInfor.isBlank()) {
            String[] parts = orderInfor.split(" - ");
            if (parts.length >= 3) {
                String toName = parts[0].trim();
                String toPhone = parts[1].trim();
                String toAddressFull = parts[2].trim();
                dto.setTo_name(toName);
                dto.setTo_phone(toPhone);
                dto.setTo_address(toAddressFull);
                // Tách tỉnh/thành, quận, phường từ địa chỉ
                String[] addressParts = toAddressFull.split(",");
                if (addressParts.length >= 3) {
                    dto.setTo_ward_name(addressParts[addressParts.length - 3].trim());
                    dto.setTo_district_name(addressParts[addressParts.length - 2].trim());
                    dto.setTo_province_name(addressParts[addressParts.length - 1].trim());
                }
            }
        }

        int totalProductCost = listOrderItemToCreateGHN.stream()
        .mapToInt(item -> (int) item.getTotal())
        .sum();


        // 6. Chi tiết đơn hàng
        dto.setCod_amount((int) order.getFinalTotal());
        dto.setContent("Theo New York Times"); // hoặc nội dung động tùy bạn
        dto.setLength(12);
        dto.setWidth(12);
        dto.setHeight(12);
        dto.setWeight(500);
        dto.setCod_failed_amount(2000);
        dto.setPick_station_id(1444);
        dto.setDeliver_station_id(null);
        dto.setInsurance_value(totalProductCost);
        dto.setService_type_id(2);
        dto.setCoupon(null);

        // 7. Thời gian lấy hàng
        dto.setPickup_time(System.currentTimeMillis() / 1000); // unix timestamp
        dto.setPick_shift(List.of(2));

        // 8. Danh sách sản phẩm
        dto.setItems(itemDTOs);

        return dto;
    }


@Transactional
    public String createGhnOrderCodeFromOrder(Orders order, String requiredNote) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);
        headers.set("ShopId", ghnShopId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        GhnCreateOrderRequestDTO dto = buildGhnDTOFromOrder(order, requiredNote);
        HttpEntity<GhnCreateOrderRequestDTO> request = new HttpEntity<>(dto, headers);
    System.out.println("GHN Request: " + dto);
        ResponseEntity<Map> response = restTemplate.postForEntity(GHN_CREATE_ORDER_URL, request, Map.class);
    System.err.println("GHN Response: " + response.getBody());
        if (response.getBody() != null && ((Integer) response.getBody().get("code")) == 200) {
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            return data != null ? (String) data.get("order_code") : null;
        } else {
            throw new RuntimeException("GHN order creation failed: " + response.getBody().get("message"));
        }

    }

    @Transactional
    public String createGhnOrderCodeFromOrder(Orders order) {
        // Gọi phiên bản đầy đủ với giá trị mặc định
        return createGhnOrderCodeFromOrder(order, "KHONGCHOXEMHANG");
}

    @Transactional
    public boolean cancelGhnOrder(String orderCode) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Token", ghnToken);
        headers.set("ShopId", ghnShopId);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("order_codes", List.of(orderCode));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/switch-status/cancel";

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        System.out.println("Cancel GHN Response: " + response.getBody());

        if (response.getBody() != null && ((Integer) response.getBody().get("code")) == 200) {
            return true; // hủy thành công
        } else {
            throw new RuntimeException("GHN order cancel failed: " + response.getBody());
        }
    }

}
