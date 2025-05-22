package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
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
    
    @Column(name = "trace_event_id")
    private String traceEventId;
    @Column(name = "package_name")
    private String packageName;
    @Column(name = "xe_event_name")
    private String xeEventName;
   





}
