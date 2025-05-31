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
public class PromotionProductDTOS {
    private int id;
    private int requireQty;
    private boolean isGift;
    private int costShare;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int productItems;
    private int promotions;
}
