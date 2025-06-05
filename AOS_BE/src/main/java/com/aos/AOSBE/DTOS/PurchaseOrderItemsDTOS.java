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
public class PurchaseOrderItemsDTOS {
    private int id;
    private int qty;
    private double cost;
    private double totalCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int productItems;
    private int purchaseOrders;
}
