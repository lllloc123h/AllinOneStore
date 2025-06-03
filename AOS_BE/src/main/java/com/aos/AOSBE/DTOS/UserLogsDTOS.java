package com.aos.AOSBE.DTOS;
import jakarta.persistence.*;

import java.time.*;
import java.math.*;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogsDTOS {
    private int id;
    private int userId;
    private String action;
    private String description;
    private String ipAddress;
    private String userAgent;
    private LocalDate createdAt;
    private String module;
}
