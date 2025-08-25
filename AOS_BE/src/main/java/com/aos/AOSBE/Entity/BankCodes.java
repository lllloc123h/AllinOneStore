package com.aos.AOSBE.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Bank_Codes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankCodes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id")
	private int id;

	@Column(name = "bank_code")
	private String bankCode;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "momo_numeric_code")
	private String momoNumericCode;

	@Column(name = "logo_Url")
	private String logoUrl;
}
