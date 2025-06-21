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
public class ReturnsDTOS {
    private int id;
    private int qty;
    private String reason;
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String videoUrl;
    private boolean isReturnedMoney;
    private double refundAmount;
    private String returnType;
    private String refundType;
    private String status;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int orderItems;
}
