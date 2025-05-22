package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trace_xe_event_map")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraceXeEventMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trace_event_id")
    private String trace_event_id;
    @Column(name = "package_name")
    private String package_name;
    @Column(name = "xe_event_name")
    private String xe_event_name;
   




}
