package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseProductsDTOS {
	private int id;
	private String name;
	private String material;
	private String mainImageUrl;
	private boolean isCustom;
	private int turnBuy;
	private int rating;
	private boolean isActive;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String categories;
}
