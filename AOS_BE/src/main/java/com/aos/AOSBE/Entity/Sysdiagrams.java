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
@Table(name = "sysdiagrams")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sysdiagrams {
    @Id
    
	
    @Column(name = "name")
    private String name;
	
    @Column(name = "principal_id")
    private int principalId;
	
    @Column(name = "diagram_id")
    private int diagramId;
	
    @Column(name = "version")
    private int version;
	
    @Column(name = "definition")
    private String definition;
   





}
