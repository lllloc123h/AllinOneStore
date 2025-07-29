package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTOS {
	private int id;
	private String keyMessage;
	private String notification;
	private LocalDateTime createdAt;
	private String accounts;
}
