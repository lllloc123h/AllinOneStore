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
public class RolesDTOS {
    private int id;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private int accounts;
    private int authorities;
}
