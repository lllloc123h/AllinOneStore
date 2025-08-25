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
public class BankCodesDTOS {
    private int id;
    private String bankCode;
    private String bankName;
    private String momoNumericCode;
    private String logoUrl;
}
