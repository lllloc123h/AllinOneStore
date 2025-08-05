package com.aos.AOSBE.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GhnCreateOrderRequestDTO {
    private int payment_type_id;
    private String note;
    private String required_note;
    private String return_phone;
    private String return_address;
    private int return_district_id;
    private String return_ward_code;
    private String client_order_code;
    
    private String from_name;
    private String from_phone;
    private String from_address;
    private String from_ward_name;
    private String from_district_name;
    private String from_province_name;
    
    private String to_name;
    private String to_phone;
    private String to_address;
    private String to_ward_name;
    private String to_district_name;
    private String to_province_name;

    private int cod_amount;
    private String content;
    private int length;
    private int width;
    private int height;
    private int weight;
    private int cod_failed_amount;
    private int pick_station_id;
    private Integer deliver_station_id;
    private int insurance_value;
    private int service_type_id;
    private String coupon;
    private Long pickup_time;
    private List<Integer> pick_shift;
    private List<GhnItemDTO> items;

    @Data
    public static class GhnItemDTO {
        private String name;
        private String code;
        private int quantity;
        private int price;
        private int length;
        private int width;
        private int height;
        private int weight;
        private GhnCategory category;

        @Data
        public static class GhnCategory {
            private String level1;
        }
    }
}
