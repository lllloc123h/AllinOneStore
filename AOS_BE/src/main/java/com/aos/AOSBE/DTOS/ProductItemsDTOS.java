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
public class ProductItemsDTOS {
    private int id;
    private double cost;
    private double price;
    private int turnBuy;
    private String description;
    private String sku;
    private int safetyStock;
    private int qty;
    private LocalDateTime sellStart;
    private LocalDateTime sellEnd;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int baseProducts;
}
