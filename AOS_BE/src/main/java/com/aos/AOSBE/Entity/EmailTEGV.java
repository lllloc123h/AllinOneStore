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
@Table(name = "Email_TEGV")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailTEGV {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	
    @Column(name = "id")
    private int id;
	
    @Column(name = "Name_TEGV")
    private String nameTEGV;
	
    @Column(name = "Email_TEGV")
    private String emailTEGV;
}
