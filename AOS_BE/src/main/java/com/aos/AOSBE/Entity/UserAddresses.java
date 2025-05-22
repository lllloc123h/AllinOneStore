package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

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
    @Column(name = "account_email")
    private String accountEmail;
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
    @Column(name = "isdefault")
    private boolean isdefault;
    @Column(name = "note")
    private String note;
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @Column(name = "update_at")
    private LocalDateTime updateAt;
   





}
