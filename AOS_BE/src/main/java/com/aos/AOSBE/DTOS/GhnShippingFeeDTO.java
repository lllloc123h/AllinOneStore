package com.aos.AOSBE.DTOS;

import lombok.Data;

@Data
public class GhnShippingFeeDTO {
    private int service_id;
    private int to_district_id;
    private String to_ward_code;
}
