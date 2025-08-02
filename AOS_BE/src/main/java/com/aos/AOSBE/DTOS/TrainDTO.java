package com.aos.AOSBE.DTOS;

import java.util.List;

import com.aos.AOSBE.Entity.ProductItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainDTO {
	private int UserID;
	private String name;
	private String gender;
	private int birthday;
	private List<ProductItems> listProductViewMostByGender;
	private List<ProductItems> listProductBoughtMostByGender;
	private List<ProductItems> listProductAddToCartMostByGender;

	private List<ProductItems> listProductViewByBirthday;
	private List<ProductItems> listProductBoughtByBirthday;
	private List<ProductItems> listProductAddToCartByBirthday;
//    viewCountLast30Days
//addToCartCountLast30Days
//wishlistCountLast30Days
//conversionRateLast30Days (tỷ lệ từ xem → mua)
//refundAmountLast30Days (nếu cần dự đoán tổn thất do hoàn trả)
}
