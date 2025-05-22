package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "variant_values")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantValues {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "variant_name")
    private String variant_name;
    @Column(name = "value")
    private String value;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "update_at")
    private LocalDateTime update_at;
   




}
