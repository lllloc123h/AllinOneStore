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
public class ReviewsDTOS {
    private int id;
    private int rating;
    private String comment;
    private String images;
    private LocalDate createdAt;
    private int accounts;
    private int productItems;
}
