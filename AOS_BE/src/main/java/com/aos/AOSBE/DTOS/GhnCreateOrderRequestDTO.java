package com.aos.AOSBE.DTOS;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class GhnCreateOrderRequestDTO {
    private int payment_type_id;
    private String note;
    private String required_note;
    private String return_phone;
    private String return_address;
    private Integer return_district_id;
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
    private Integer pick_station_id;
    private Integer deliver_station_id;
    private int insurance_value;
    private int service_type_id;
    private String coupon;
    private Long pickup_time;
    private List<Integer> pick_shift;
    private List<Item> items;

    @Data
    public static class Item {
        private String name;
        private String code;
        private int quantity;
        private int price;
        private int length;
        private int width;
        private int height;
        private int weight;
        private Category category;
    }

    @Data
    public static class Category {
        private String level1;
    }
}
