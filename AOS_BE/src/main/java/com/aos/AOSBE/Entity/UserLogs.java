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
@Table(name = "UserLogs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id\&quot;)
    private int id;
	
    @Column(name = "user_id\&quot;)
    private int userId;
	
    @Column(name = "action\&quot;)
    private String action;
	
    @Column(name = "description\&quot;)
    private String description;
	
    @Column(name = "ip_address\&quot;)
    private String ipAddress;
	
    @Column(name = "user_agent\&quot;)
    private String userAgent;
	@CreationTimestamp
    @Column(name = "created_at\&quot;,updatable &#61; false)
    private LocalDateTime createdAt;
	
    @Column(name = "module\&quot;)
    private String module;
   





}
