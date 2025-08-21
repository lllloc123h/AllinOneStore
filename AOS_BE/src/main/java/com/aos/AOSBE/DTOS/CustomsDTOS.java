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
public class CustomsDTOS {
    private Integer id;
    private int accountId;
    private String canvasJson;
    private String imageUrl;
    private String designName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProductItemsDTOS productItems;
    private OrdersDTOS order;
}
