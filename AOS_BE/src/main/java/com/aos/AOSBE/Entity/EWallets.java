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
@Table(name = "e_wallets")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EWallets {
    @Id
    
	
    @Column(name = "id")
    private String id;
	
    @Column(name = "balance")
    private double balance;
	
    @Column(name = "wallet_type")
    private String walletType;
	
    @Column(name = "is_active")
    private boolean isActive;
	
    @Column(name = "code_activce")
    private String codeActivce;
	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;
}
