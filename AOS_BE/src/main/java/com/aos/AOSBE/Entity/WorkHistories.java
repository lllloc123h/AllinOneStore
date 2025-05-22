package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "work_histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkHistories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "note")
    private String note;
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "end_at")
    private LocalDateTime endAt;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Accounts accounts;





}
