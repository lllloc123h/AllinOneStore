package com.aos.AOSBE.DTOS;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthoritiesDTOS {
	private int id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private String accounts;
	private List<String> listRoles;
	private String roles;

	public AuthoritiesDTOS(int id, LocalDateTime createdAt, LocalDateTime updatedAt, String accounts,
			List<String> listRoles) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.accounts = accounts;
		this.listRoles = listRoles;
	}

	public AuthoritiesDTOS(int id, LocalDateTime createdAt, LocalDateTime updatedAt, String accounts, String roles) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.accounts = accounts;
		this.roles = roles;
	}

}
