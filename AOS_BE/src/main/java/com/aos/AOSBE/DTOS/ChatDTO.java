package com.aos.AOSBE.DTOS;

import lombok.Data;

import java.util.Map;

@Data
public class ChatDTO {
    private String message;
    private Map<String, String> filters;
}
