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
public class PurchaseOrdersDTOS {
    private int id;
    private LocalDate orderDate;
    private LocalDate expectedDate;
    private LocalDate receivedDate;
    private double total;
    private String status;
    private String note;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
