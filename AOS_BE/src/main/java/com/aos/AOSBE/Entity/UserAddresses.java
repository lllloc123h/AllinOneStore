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
@Table(name = "user_addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "recipient_name")
    private String recipientName;
	
    @Column(name = "phone")
    private String phone;
	
    @Column(name = "province")
    private String province;
	
    @Column(name = "district")
    private String district;
	
    @Column(name = "ward")
    private String ward;
	
    @Column(name = "street")
    private String street;
	
    @Column(name = "label")
    private String label;
	
    @Column(name = "is_default")
    private boolean isDefault;
	
    @Column(name = "note")
    private String note;
	@CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;





}
