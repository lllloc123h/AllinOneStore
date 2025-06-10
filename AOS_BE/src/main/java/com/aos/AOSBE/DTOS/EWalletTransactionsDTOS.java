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
public class EWalletTransactionsDTOS {
    private int id;
    private double amount;
    private String transactionType;
    private int relatedWalletId;
    private String description;
    private LocalDateTime createdAt;
    private int eWallets;
}
