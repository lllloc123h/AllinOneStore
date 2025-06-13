@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderCalculationAPI {

    private final PromotionService promotionService;
    private final ProductItemService productItemService;
    private final ShippingService shippingService;

    @PostMapping("/calculate")
    public ResponseEntity<OrderCalculationResponse> calculate(@RequestBody OrderCalculationRequest req) {
        List<Long> productIds = req.getProductItemIds();
        List<Integer> quantities = req.getQuantities();

        double productTotal = productItemService.calculateTotal(productIds, quantities);
        double comboDiscount = promotionService.applyComboPromotion(productIds, quantities);
        double afterCombo = productTotal - comboDiscount;

        double couponDiscount = promotionService.applyDiscountCoupon(req.getDiscountCouponCode(), afterCombo);
        double estimatedShippingFee = shippingService.calculateFee(req.getShippingMethodId(), productIds, quantities);
        double actualShippingFee = promotionService.applyFreeshipCoupon(req.getFreeshipCouponCode(), estimatedShippingFee);

        double finalTotal = afterCombo - couponDiscount + actualShippingFee;

        OrderCalculationResponse res = new OrderCalculationResponse();
        res.setProductTotal(productTotal);
        res.setComboDiscount(comboDiscount);
        res.setCouponDiscount(couponDiscount);
        res.setEstimatedShippingFee(estimatedShippingFee);
        res.setActualShippingFee(actualShippingFee);
        res.setFinalTotal(finalTotal);

        return ResponseEntity.ok(res);
    }
}
