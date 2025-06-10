package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "e_wallet_transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EWalletTransactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "amount")
    private double amount;
	
    @Column(name = "transaction_type")
    private String transactionType;
	
    @Column(name = "related_wallet_id")
    private int relatedWalletId;
	
    @Column(name = "description")
    private String description;
	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
   
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private EWallets eWallets;





}
