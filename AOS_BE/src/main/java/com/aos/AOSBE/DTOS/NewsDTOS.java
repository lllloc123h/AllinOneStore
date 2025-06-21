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
public class NewsDTOS {
    private int id;
    private String imageUrl;
    private boolean isHome;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
