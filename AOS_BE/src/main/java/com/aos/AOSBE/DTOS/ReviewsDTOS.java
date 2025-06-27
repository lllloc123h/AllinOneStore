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
    private String imageUrl1;
    private String imageUrl2;
    private String imageUrl3;
    private String videoUrl;
    private LocalDateTime createdAt;
    private int accounts;
    private int productItems;
}
