package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesDTOS {
	private int id;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String catalogs;
}
