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
public class VariantValuesDTOS {
    private int id;
    private String signalSku;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String variants;
}
