package com.aos.AOSBE.DTOS;

import lombok.Data;

import java.util.List;

@Data
public class GeneralStatsDTO {
    private Integer countOrders;
    //es doanh thu
    private Double estimatedRevenue;
    // es discount on order
    private Double estimatedDiscountOnOrder;
    //es chênh lệch vận chuyển
    private Double estimatedShippingDifference;
    //es chênh lệch giá
    private Double estimatedPriceDifference;
    //es chi phí
    private Double estimatedCost;

    // thực tế
    private Integer countActualOrders;
    private Double actualRevenue;
    private Double actualDiscountOnOrder;
    private Double actualShippingDifference;
    private Double actualPriceDifference;
    private Double actualCost;

    // returned order
    private Integer countReturnedOrders;
    private Double returnedAndRefundOrders;
    // thiệt hại do phí vận chuyển
    private Double shippingCostLost;
    // damage, lost, exception
    // cancel order;
    private List<Double> cancelAndPaidOrders;
    // cancel order unpaid
    private List<Double> cancelAndRefundOrders;

    private Integer countDamageLostException;
    private Double damageLostException;
}
