package com.aos.AOSBE.DTOS;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderExportDto {
    private String orderCode;
    private LocalDateTime createdAt;
    private String fullname;
    private String email;
    private String phone;
    private String address;
    private String paymentStatus;
    private String shippingStatus;
    private LocalDateTime shippedDate;
    private BigDecimal finalTotal;
    
    public OrderExportDto(String ghnOrderCode, LocalDateTime createdAt, String fullname, String email, String phone,
            String address, String paymentStatus, String shippingStatus,LocalDateTime shippedDate, BigDecimal finalTotal) {
this.orderCode = ghnOrderCode;
this.createdAt = createdAt;
this.fullname = fullname;
this.email = email;
this.phone = phone;
this.address = address;
this.paymentStatus = paymentStatus;
this.shippingStatus = shippingStatus;
this.shippedDate = shippedDate;
this.finalTotal = finalTotal;
}
}
