package com.aos.AOSBE.Entity;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
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
    
    @Column(name = "trace_column_id")
    private String traceColumnId;
    @Column(name = "package_name")
    private String packageName;
    @Column(name = "xe_action_name")
    private String xeActionName;
   





}
