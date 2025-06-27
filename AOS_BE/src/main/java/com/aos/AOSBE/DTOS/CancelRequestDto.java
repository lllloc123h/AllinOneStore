package com.aos.AOSBE.DTOS;

import lombok.Data;

@Data
public class CancelRequestDto {
    private String reason;
    private String refundType; 
}
