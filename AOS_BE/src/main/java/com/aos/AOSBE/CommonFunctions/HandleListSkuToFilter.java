package com.aos.AOSBE.CommonFunctions;

public class HandleListSkuToFilter {
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
}
