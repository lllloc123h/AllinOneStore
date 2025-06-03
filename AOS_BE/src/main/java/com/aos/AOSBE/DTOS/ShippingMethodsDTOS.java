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
public class ShippingMethodsDTOS {
    private int id;
    private int orderId;
    private String name;
    private String description;
    private boolean isActive;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
