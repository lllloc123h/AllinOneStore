package com.aos.AOSBE.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;
import org.springframework.beans.factory.annotation.Value;

import com.aos.AOSBE.Entity.Coupons;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Entity.PaymentMethods;
import com.aos.AOSBE.Entity.ShippingMethods;
import com.aos.AOSBE.Repository.CouponsRepository;
import com.aos.AOSBE.Repository.OrdersRepository;
import com.aos.AOSBE.Repository.PaymentMethodsRepository;
import com.aos.AOSBE.Repository.ShippingMethodsRepository;

@Service
public class OrdersService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private CouponsRepository couponsRepository;
	@Autowired
	private PaymentMethodsRepository paymentMethodsRepository;
	@Autowired
	private ShippingMethodsRepository shippingMethodsRepository;

	@Value("${GHN_TOKEN}")
	private String ghnToken;

	@Value("${GHN_SHOPID}")
	private String ghnShopId;
	
	public Page<Orders> ordersFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<Orders> spec = specBuilder.buildFilter(filters);
		return ordersRepository.findAll(spec, pageable);
	}

	@Transactional
	public Orders ordersSave(Orders orders) {
	    try {
	        // Xử lý Payment Method
	        if (orders.getPaymentMethods() == null && orders.getPaymentMethodId() != null) {
	            PaymentMethods pm = paymentMethodsRepository.findById(orders.getPaymentMethodId())
	                .orElseThrow(() -> new IllegalArgumentException("Phương thức thanh toán không hợp lệ."));
	            orders.setPaymentMethods(pm);
	        }

	        // Xử lý Shipping Method
	        if (orders.getShippingMethods() == null && orders.getShippingMethodId() != null) {
	            ShippingMethods sm = shippingMethodsRepository.findById(orders.getShippingMethodId())
	                .orElseThrow(() -> new IllegalArgumentException("Phương thức vận chuyển không hợp lệ."));
	            orders.setShippingMethods(sm);
	        }

	        // Kiểm tra đã chọn phương thức thanh toán
	        if (orders.getPaymentMethods() == null) {
	            throw new IllegalArgumentException("Phải chọn phương thức thanh toán.");
	        }

	        // Thiết lập trạng thái thanh toán
	        String paymentMethodName = orders.getPaymentMethods().getName();
	        if ("Thanh toán qua ví điện tử".equalsIgnoreCase(paymentMethodName)) {
	            orders.setPaymentStatus("Đã thanh toán");
	        } else {
	            orders.setPaymentStatus("Chưa thanh toán");
	        }

	        // Thiết lập trạng thái giao hàng mặc định
	        if (orders.getShippingStatus() == null || orders.getShippingStatus().isEmpty()) {
	            orders.setShippingStatus("Chờ xác nhận");
	        }

	        // Kiểm tra mã giảm giá
	        String couponCode = orders.getDiscountCouponCode();
	        if (couponCode != null && !couponCode.trim().isEmpty()) {
	            Optional<Coupons> optionalCoupon = couponsRepository.findByCode(couponCode);
	            if (optionalCoupon.isEmpty()) {
	                throw new IllegalArgumentException("Mã giảm giá không tồn tại.");
	            }
	            Coupons coupon = optionalCoupon.get();
	            if (!coupon.isActive()) {
	                throw new IllegalArgumentException("Mã giảm giá không còn hiệu lực.");
	            }

	            long usageCount = ordersRepository.countCouponUsage((long) orders.getAccounts().getId(), couponCode);
	            if (usageCount >= coupon.getUsagePerCustomer()) {
	                throw new IllegalStateException("Bạn đã sử dụng mã này đủ số lần cho phép.");
	            }
	        }

	        return ordersRepository.save(orders);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}




	@Transactional
	public Optional<Orders> ordersFindById(int id) {
		return ordersRepository.findById(id);
	}

	@Transactional
	public void ordersDeleteById(int id) {
		ordersRepository.deleteById(id);
	}
	@Transactional
    public Orders updateShippingStatusFromGHN(int id) {
        Optional<Orders> optionalOrder = ordersRepository.findById(id);
        if (optionalOrder.isEmpty()) {
            throw new IllegalArgumentException("Không tìm thấy đơn hàng với ID: " + id);
        }

        Orders order = optionalOrder.get();
        String orderCode = order.getGhnOrderCode();

        if (orderCode == null || orderCode.isEmpty()) {
            throw new IllegalArgumentException("Đơn hàng chưa có mã vận đơn GHN.");
        }

        try {
            String url = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Token", ghnToken);
            headers.set("ShopId", ghnShopId);

            String requestBody = "{\"order_code\": \"" + orderCode + "\"}";
            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                Map<String, Object> data = (Map<String, Object>) responseBody.get("data");
                String status = (String) data.get("status");

                order.setShippingStatus(status);
                ordersRepository.save(order);
                return order;
            } else {
                throw new RuntimeException("GHN trả về lỗi: " + response.getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi gọi GHN API: " + e.getMessage());
        }
    }
}