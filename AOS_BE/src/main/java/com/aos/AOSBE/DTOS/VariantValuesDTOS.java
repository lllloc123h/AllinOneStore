package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantValuesDTOS {
	private int id;
	private String name;
	private String signalSku;
	private String description;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int variants;
}
