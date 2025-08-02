package com.aos.AOSBE.DTOS;

import lombok.Data;

@Data
public class GeneralStatsDTO {
    private Double grossRevenue;
    // tổng doanh thu khi nhận đc hàng
    private Double rawNetRevenue;
    // tổng chiết trừ giả giá, vận chuyển
    private Double totalCostForDiscount;
    // đã chiết trừ giảm giá, vận chuyển
    private Double NetRevenue;
    // số đơn bán thực tế, đã giao
    private Long countDeliveredOrders;


    //count số đơn đã return
    private Long countReturnedOrders;
    // tổng giá trị số đơn hàng đã hoàn trả hoặc kh nhận hàng
    private Double totalOrderReturned;
    // các đơn hàng lẻ về return
    private Double totalReturnedAmount;



    // tổng phí vận chuyển trên hóa đơn ước tính
    private Double totalActualShippingFee;
    // tổng phí vận chuyển trên hóa đơn vận chuyển ước tính
    private Double totalEstimatedShippingFee;
    // tông giám giá vận chuyển thực tế, đã giao
    private Double totalActualShippingFeeDelivered;

    // tổng chiết trừ ước tính
    private Double totalEstimatedDiscountValue;
    // tổng chiết trừ đã giao
    private Double totalDiscountValueDilivered;

    private Double totalCostProducts;
}
