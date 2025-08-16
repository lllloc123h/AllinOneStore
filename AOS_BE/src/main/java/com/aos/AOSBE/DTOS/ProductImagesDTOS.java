package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImagesDTOS {
	private int id;
	private String imageUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private int productItems;
	private boolean isDefault;

}
