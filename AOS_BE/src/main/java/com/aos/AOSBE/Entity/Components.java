package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "components")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Components {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "material")
    private String material;
    @Column(name = "color")
    private String color;
    @Column(name = "unit")
    private String unit;
    @Column(name = "description")
    private String description;
    @Column(name = "qty")
    private int qty;
    @Column(name = "type")
    private String type;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   





}
