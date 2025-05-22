package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "price_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "item_id")
    private int item_id;
    @Column(name = "price")
    private double price;
    @Column(name = "create_at")
    private LocalDateTime create_at;
   




}
