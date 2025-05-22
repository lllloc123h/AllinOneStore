package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
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
    private String variantName;
    @Column(name = "value")
    private String value;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Accounts accounts;





}
