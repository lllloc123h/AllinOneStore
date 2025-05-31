package com.aos.AOSBE.DTOS;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDTOS {
    private int id;
    private int promotionId;
    private int qty;
    private double cost;
    private boolean isGift;
    private double sellingPrice;
    private double total;
    private int couponCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int orders;
    private int productItems;
}
