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
@Table(name = "user_product_events")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductEvents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "event_type")
    private String eventType;
	
    @Column(name = "event_time")
    private LocalDateTime eventTime;
	
    @Column(name = "session_id")
    private String sessionId;
	
    @Column(name = "device")
    private String device;
	
    @Column(name = "ip_address")
    private String ipAddress;
	
    @Column(name = "user_agent")
    private String userAgent;
	
    @Column(name = "position_in_list")
    private int positionInList;
	
    @Column(name = "time_spent_seconds")
    private int timeSpentSeconds;
	
    @Column(name = "referrer")
    private String referrer;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Accounts accounts;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductItems productItems;
}
