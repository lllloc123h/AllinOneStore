package com.aos.AOSBE.DTOS;

import lombok.Data;

import java.util.List;
@Data
public class GroupProductDTO {
     private   BaseProductsDTOS baseProduct;
       private List<ProductItemsDTOS> items;
}
