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
public class PromotionProductsDTOS {
    private int id;
    private int requireQty;
    private boolean isGift;
    private double costShare;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int productItems;
    private int promotions;
}
