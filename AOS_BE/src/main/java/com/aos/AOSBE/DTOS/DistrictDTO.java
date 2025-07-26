package com.aos.AOSBE.DTOS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistrictDTO {

    @JsonProperty("DistrictID")
    private int districtID;

    @JsonProperty("DistrictName")
    private String districtName;
}
