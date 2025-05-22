package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cost_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostHistories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "item_id")
    private int itemId;
    @Column(name = "cost")
    private double cost;
    @Column(name = "create_at")
    private LocalDateTime createAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "catalog_name")
    private Catalogs catalogs;





}
