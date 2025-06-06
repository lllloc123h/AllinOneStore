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
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "user_id")
    private int userId;
	
    @Column(name = "action")
    private String action;
	
    @Column(name = "description")
    private String description;
	
    @Column(name = "ip_address")
    private String ipAddress;
	
    @Column(name = "user_agent")
    private String userAgent;
	@CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
	
    @Column(name = "module")
    private String module;
   





}
