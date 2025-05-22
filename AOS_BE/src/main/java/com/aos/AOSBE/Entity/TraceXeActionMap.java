package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trace_xe_action_map")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraceXeActionMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trace_column_id")
    private String trace_column_id;
    @Column(name = "package_name")
    private String package_name;
    @Column(name = "xe_action_name")
    private String xe_action_name;
   




}
