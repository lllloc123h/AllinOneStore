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
public class PromotionsDTOS {
    private int id;
    private String name;
    private String description;
    private String type;
    private String discountType;
    private double discountValue;
    private double comboPrice;
    private int usageLimit;
    private LocalDate startAt;
    private LocalDate endAt;
    private boolean isActive;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
