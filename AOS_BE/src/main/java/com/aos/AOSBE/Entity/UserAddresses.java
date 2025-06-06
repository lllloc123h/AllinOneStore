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
	
    @Column(name = "id\&quot;)
    private int id;
	
    @Column(name = "recipient_name\&quot;)
    private String recipientName;
	
    @Column(name = "phone\&quot;)
    private String phone;
	
    @Column(name = "province\&quot;)
    private String province;
	
    @Column(name = "district\&quot;)
    private String district;
	
    @Column(name = "ward\&quot;)
    private String ward;
	
    @Column(name = "street\&quot;)
    private String street;
	
    @Column(name = "label\&quot;)
    private String label;
	
    @Column(name = "is_default\&quot;)
    private boolean isDefault;
	
    @Column(name = "note\&quot;)
    private String note;
	@CreationTimestamp
    @Column(name = "created_at\&quot;,updatable &#61; false)
    private LocalDateTime createdAt;
	@UpdateTimestamp
    @Column(name = "updated_at\&quot;)
    private LocalDateTime updatedAt;
   
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Accounts accounts;





}
