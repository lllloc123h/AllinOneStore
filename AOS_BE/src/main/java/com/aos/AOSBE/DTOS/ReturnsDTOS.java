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
    private String reason;
    private String image1;
    private String image2;
    private String image3;
    private String isReturnedMoney;
    private String isReturnedItem;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int orderItems;
}
