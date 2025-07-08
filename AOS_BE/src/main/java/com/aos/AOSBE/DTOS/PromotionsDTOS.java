package com.aos.AOSBE.DTOS;

import java.time.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionsDTOS {
    private int id;
    private String name;
    private String description;
    private String type;
    private String discountType;
    private double discountValue;
    private double comboPrice;
    private int qty;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
