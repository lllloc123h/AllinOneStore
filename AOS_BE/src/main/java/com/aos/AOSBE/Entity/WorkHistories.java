package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
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
    @Column(name = "account_email")
    private String account_email;
    @Column(name = "note")
    private String note;
    @Column(name = "start_at")
    private LocalDateTime start_at;
    @Column(name = "end_at")
    private LocalDateTime end_at;
   




}
