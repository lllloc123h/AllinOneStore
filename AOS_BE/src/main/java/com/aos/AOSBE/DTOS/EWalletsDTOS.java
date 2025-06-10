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
public class EWalletsDTOS {
    private int id;
    private double balance;
    private String walletType;
    private boolean isActive;
    private LocalDateTime createdAt;
    private int accounts;
}
