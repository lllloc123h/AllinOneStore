package com.aos.AOSBE.DTOS;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateComboDTO {
private Integer comboQty;
private UUID comboGroupId;
private String type;
}
