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
public class BaseProductsDTOS {
    private int id;
    private String name;
    private String material;
    private String mainImage;
    private boolean isCustom;
    private int turnBuy;
    private int rating;
    private boolean isPromote;
    private boolean isActive;
    private List<Double> listPrice;
}
