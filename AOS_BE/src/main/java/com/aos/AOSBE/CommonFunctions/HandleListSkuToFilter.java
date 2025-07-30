package com.aos.AOSBE.CommonFunctions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.Entity.VariantValues;
import com.aos.AOSBE.Repository.VariantValuesRepository;

@Component
public class HandleListSkuToFilter {
	@Autowired
	private VariantValuesRepository variantValuesRepository;

	public String buildKeyFilter(String skyColorLike, String skySizeLike) {
		String result = "";
		String[] skuColorLikeList = skyColorLike.split("-");
		String[] skuSizeLikeList = skySizeLike.split("-");
		if (skuColorLikeList != null && skuSizeLikeList != null) {
			for (String itemColor : skuColorLikeList) {
				for (String itmeSize : skuSizeLikeList) {
					result += itemColor + "-" + itmeSize + ",";
				}
			}
		} else if (skuColorLikeList != null && skuSizeLikeList == null) {
			for (String itemColor : skuColorLikeList) {
				result += itemColor + ",";
			}
		} else if (skuColorLikeList == null && skuSizeLikeList != null) {
			for (String itmeSize : skuSizeLikeList) {
				result += itmeSize + ",";
			}
		}

		return result.substring(0, result.length() - 1);
	}

	public String getDescriptionOfSku(String sku) {
		String[] result = new String[sku.split("-").length - 1];
		String[] signalSku = sku.split("-");
		System.out.println(signalSku[0] + " " + signalSku[1]);
		for (int i = 1; i < signalSku.length; i++) {
			VariantValues var = variantValuesRepository.findBySignalSku(signalSku[i].trim());
			if (var != null) {
				result[i - 1] = var.getDescription();
			}
		}
		return String.join("-", result);
	}
}
