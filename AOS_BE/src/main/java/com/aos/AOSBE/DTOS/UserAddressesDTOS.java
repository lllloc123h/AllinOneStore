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
public class UserAddressesDTOS {
    private int id;
    private String recipientName;
    private String phone;
    private String province;
    private String district;
    private String ward;
    private String street;
    private String label;
    private boolean isDefault;
    private String note;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private int accounts;
}
