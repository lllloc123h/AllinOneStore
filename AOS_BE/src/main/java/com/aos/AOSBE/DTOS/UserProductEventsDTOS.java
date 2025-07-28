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
public class UserProductEventsDTOS {
    private int id;
    private String eventType;
    private LocalDateTime eventTime;
    private String sessionId;
    private String device;
    private String ipAddress;
    private String userAgent;
    private int positionInList;
    private int timeSpentSeconds;
    private String referrer;
    private int accounts;
    private int productItems;
}
