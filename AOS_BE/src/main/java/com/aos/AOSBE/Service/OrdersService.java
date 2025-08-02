package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrdersService {
	@Autowired
<<<<<<< Updated upstream
    private OrdersRepository ordersRepository;
=======
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private CouponsRepository couponsRepository;
	@Autowired
	private PaymentMethodsRepository paymentMethodsRepository;
	@Autowired
	private ShippingMethodsRepository shippingMethodsRepository;
	@Autowired
	private ReturnsRepository returnsRepository;
	@Autowired
	private ProductItemsRepository productItemsRepository;
	@Autowired
	private GhnService ghnService;

	@Autowired
	private OrderItemsRepository orderItemsRepository; // nếu cần

	private final String ghnToken = System.getProperty("GHN_TOKEN");
	private final String ghnShopId = System.getProperty("GHN_SHOPID");

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
				orders.setPaymentStatus("Chưa thanh toán");
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
			// Lưu đơn hàng trước để có ID (vì OrderItems cần `orders`)
	        Orders savedOrder = ordersRepository.save(orders);

	        // Kiểm tra và xử lý từng OrderItem
	        if (orders.getOrderItems() != null && !orders.getOrderItems().isEmpty()) {
	            for (OrderItems item : orders.getOrderItems()) {
	                // Gắn đơn hàng cho từng item
	                item.setOrders(savedOrder);

	                // Lấy productItem để cập nhật tồn kho
	                ProductItems productItem = item.getProductItems();

	                if (productItem == null) {
	                    throw new IllegalArgumentException("Không tìm thấy sản phẩm cho đơn hàng.");
	                }

	                int orderedQty = item.getQty();
	                int currentStock = productItem.getQty();

	                if (orderedQty > currentStock) {
	                    throw new IllegalStateException("Sản phẩm " + productItem.getId() + " không đủ tồn kho.");
	                }

	                productItem.setQty(currentStock - orderedQty);

	                // Lưu lại cập nhật tồn kho
	                productItemsRepository.save(productItem);
	            }

	            // Lưu tất cả order items
	            orderItemsRepository.saveAll(orders.getOrderItems());
	        }

			try {
				// Gọi GHN để tạo vận đơn và lấy order_code
				String ghnOrderCode = ghnService.createGhnOrderCodeFromOrder(savedOrder);
				savedOrder.setGhnOrderCode(ghnOrderCode);
				ordersRepository.save(savedOrder); // Lưu lại order_code vào DB
			} catch (Exception e) {
				e.printStackTrace();
			}


	        return savedOrder;

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

	public List<Orders> ordersFindByAccountAndKeyShippingStatus(int account, String key) {
		return ordersRepository.findAllByAccountAndKeyShippingStatus(account, key);
	}

	public List<Orders> ordersFindByAccountAndKeyPaymentPending(int account, String key) {
		return ordersRepository.findAllByAccountAndKeyPaymentPending(account, key, 2);
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
		System.out.println(">> GHN_TOKEN: " + ghnToken);
		System.out.println(">> GHN_SHOPID: " + ghnShopId);
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
			String url = "https://dev-online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/detail";

			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.set("Token", ghnToken);

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

	public GeneralStatsDTO getGeneralStats() {
		GeneralStatsDTO stats = new GeneralStatsDTO();
		stats.setGrossRevenue(ordersRepository.grossRevenue());
		stats.setRawNetRevenue(ordersRepository.rawNetRevenue());
		stats.setTotalCostForDiscount(ordersRepository.totalCostForDiscount());
		Double raw = ordersRepository.rawNetRevenue();
		Double totalCost = ordersRepository.totalCostForDiscount();

		stats.setNetRevenue((raw == null ? 0.0 : raw) - (totalCost == null ? 0.0 : totalCost));
		stats.setCountDeliveredOrders(ordersRepository.countDeliveredOrders());


		stats.setCountReturnedOrders(ordersRepository.countReturnedOrders());
		stats.setTotalOrderReturned(ordersRepository.totalOrderReturned());
		stats.setTotalActualShippingFee(ordersRepository.totalActualShippingFee());
		stats.setTotalEstimatedShippingFee(ordersRepository.totalEstimatedShippingFee());
		stats.setTotalActualShippingFeeDelivered(ordersRepository.totalActualShippingFeeDelivered());
		stats.setTotalReturnedAmount(returnsRepository.findTotalRefundAmount());

		stats.setTotalEstimatedDiscountValue(ordersRepository.totalEstimatedDiscountValue());
		stats.setTotalDiscountValueDilivered(ordersRepository.totalDiscountValueDelivered());

		stats.setTotalCostProducts(ordersRepository.totalCostProducts());
		return stats;
	}
	@Transactional
	public List<Orders> ordersFindByAccount(int accountId) {
		return ordersRepository.findAllByAccountsId(accountId);
	}
	public List<OrderExportDto> getOrdersForExport(LocalDateTime startDate, LocalDateTime endDate) {
		List<Object[]> listExportOrder =ordersRepository.getOrdersForExport(startDate, endDate);
		
		List<OrderExportDto> list = new ArrayList<>();
		listExportOrder.forEach(e->{
			OrderExportDto OED = new OrderExportDto();
			OED.setOrderCode((String) e[0]);
			OED.setCreatedAt(((Timestamp) e[1]).toLocalDateTime());
			OED.setFullname((String) e[2]);
			OED.setEmail((String) e[3]);
			OED.setPhone((String) e[4]);
			OED.setAddress((String) e[5]);
			OED.setPaymentStatus((String) e[6]);
			OED.setShippingStatus((String) e[7]);
			OED.setShippedDate(e[8] != null ? ((Timestamp) e[8]).toLocalDateTime() : null);
			OED.setFinalTotal((BigDecimal) e[9]);
			list.add(OED);
			});
	    return list;
	}
//	 private String orderCode;
//	    private LocalDate createdAt;
//	    private String fullname;
//	    private String email;
//	    private String phone;
//	    private String address;
//	    private String paymentStatus;
//	    private String shippingStatus;
//	    private BigDecimal finalTotal;
//	
	public ByteArrayInputStream exportOrdersToExcel(List<OrderExportDto> orders) throws IOException {
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
	        Sheet sheet = workbook.createSheet("Orders");
	        Row header = sheet.createRow(0);
	        String[] headers = {"Mã đơn", "Ngày tạo", "Tên KH", "Email", "SĐT", "Địa chỉ", "TT thanh toán", "TT đơn hàng","Ngày Giao", "Tổng tiền"};
	        for (int i = 0; i < headers.length; i++) {
	            header.createCell(i).setCellValue(headers[i]);
	        }

	        int rowIdx = 1;
	        for (OrderExportDto order : orders) {
	            Row row = sheet.createRow(rowIdx++);
	            row.createCell(0).setCellValue(order.getOrderCode());
	            row.createCell(1).setCellValue(order.getCreatedAt().toString());
	            row.createCell(2).setCellValue(order.getFullname());
	            row.createCell(3).setCellValue(order.getEmail());
	            row.createCell(4).setCellValue(order.getPhone());
	            row.createCell(5).setCellValue(order.getAddress());
	            row.createCell(6).setCellValue(order.getPaymentStatus());
	            row.createCell(7).setCellValue(order.getShippingStatus());
	            row.createCell(8).setCellValue(order.getShippedDate());
	            row.createCell(9).setCellValue(order.getFinalTotal().doubleValue());
	        }

	        workbook.write(out);
	        return new ByteArrayInputStream(out.toByteArray());
	    }
	}
>>>>>>> Stashed changes

    public List<Orders> ordersFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return ordersRepository.findAll(pageable).getContent();
    }
    @Transactional
    public Orders ordersSave(Orders orders) {
        return ordersRepository.save(orders);
    }
    public Optional<Orders> ordersFindById(int id) {
        return ordersRepository.findById(id);
    }
    @Transactional
    public void ordersDeleteById(int id) {
        ordersRepository.deleteById(id);
    }
}