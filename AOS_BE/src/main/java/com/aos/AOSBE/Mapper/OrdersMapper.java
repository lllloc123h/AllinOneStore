package com.aos.AOSBE.Mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aos.AOSBE.DTOS.OrderItemsDTOS;
import com.aos.AOSBE.DTOS.OrdersDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Entity.PaymentMethods;
import com.aos.AOSBE.Entity.ShippingMethods;
import com.aos.AOSBE.Repository.OrderItemsRepository;
import com.aos.AOSBE.Repository.PaymentMethodsRepository;
import com.aos.AOSBE.Repository.ShippingMethodsRepository;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.PaymentMethodsService;
import com.aos.AOSBE.Service.ShippingMethodsService;

@Component
public class OrdersMapper {
	@Autowired
	private AccountsService accountsService;
	@Autowired
	private PaymentMethodsService paymentMethodsService;
	@Autowired
	private ShippingMethodsService shippingMethodsService;
	@Autowired
	private ShippingMethodsRepository shippingMethodsRepository;

	@Autowired
	private PaymentMethodsRepository paymentMethodsRepository;
	@Autowired
	private OrderItemsRepository orderItemsRepository;
	@Autowired
	private OrderItemsMapper orderItemsMapper;

	public OrdersDTOS mapper(Orders entity) {
		List<OrderItemsDTOS> list = orderItemsRepository.findByOrdersId(entity.getId()).stream().map(orderItemsMapper::mapper).toList();
		return new OrdersDTOS(
				entity.getId(),
				entity.getShippingStatus(),
				entity.getEstimatedShippingFee(),
				entity.getFreeshipCouponCode(),
				entity.getActualShippingFee(),
				entity.getDiscountCouponCode(),
				entity.getDiscountValue(),
				entity.getShippedDate(),
				entity.getPaymentStatus(),
				entity.getNote(),
				entity.getPoint(),
				entity.getFinalTotal(),
				entity.getOrderInfor(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getAccounts().getId(),
				entity.getPaymentMethods().getId(), // ID
				entity.getPaymentMethods().getName(), // Name
				entity.getShippingMethods().getId(), // ID
				entity.getShippingMethods().getName(), // Name
				list,
				entity.getGhnOrderCode()
				);
	}
	public OrdersDTOS mapperForOrderDetail(Orders entity) {
		List<OrderItemsDTOS> list = orderItemsRepository
				.findByOrdersId(entity.getId())
				.stream()
				.map(orderItemsMapper::mapper)
				.collect(Collectors.toList());
		return new OrdersDTOS(
				entity.getId(),
				entity.getShippingStatus(),
				entity.getEstimatedShippingFee(),
				entity.getFreeshipCouponCode(),
				entity.getActualShippingFee(),
				entity.getDiscountCouponCode(),
				entity.getDiscountValue(),
				entity.getShippedDate(),
				entity.getPaymentStatus(),
				entity.getNote(),
				entity.getPoint(),
				entity.getFinalTotal(),
				entity.getOrderInfor(),
				entity.getCreatedAt(),
				entity.getUpdatedAt(),
				entity.getAccounts().getId(),
				entity.getPaymentMethods().getId(), // ID
				entity.getPaymentMethods().getName(), // Name
				entity.getShippingMethods().getId(), // ID
				entity.getShippingMethods().getName(), // Name
				list,
				entity.getGhnOrderCode());
	}

	public Orders mapperToObject(OrdersDTOS dto) {
		Orders order = new Orders();

		// Gán trực tiếp các field đơn giản
		order.setShippingStatus(dto.getShippingStatus());
		order.setEstimatedShippingFee(dto.getEstimatedShippingFee());
		order.setFreeshipCouponCode(dto.getFreeshipCouponCode());
		order.setActualShippingFee(dto.getActualShippingFee());
		order.setDiscountCouponCode(dto.getDiscountCouponCode());
		order.setDiscountValue(dto.getDiscountValue());
		order.setShippedDate(dto.getShippedDate());
		order.setPaymentStatus(dto.getPaymentStatus());
		order.setNote(dto.getNote());
		order.setPoint(dto.getPoint());
		order.setFinalTotal(dto.getFinalTotal());
		order.setOrderInfor(dto.getOrderInfor());
		order.setGhnOrderCode(dto.getOrderCode());

		// Gán account (nếu cần)
		Accounts acc = new Accounts();
		acc.setId(dto.getAccounts()); // Assuming this is accountId
		order.setAccounts(acc);

		// Gán paymentMethods
		PaymentMethods pm = paymentMethodsRepository.findById(dto.getPaymentMethodId()).orElseThrow(
				() -> new RuntimeException("Không tìm thấy phương thức thanh toán ID: " + dto.getPaymentMethodId()));
		order.setPaymentMethods(pm);

		// Gán shippingMethods
		ShippingMethods sm = shippingMethodsRepository.findById(dto.getShippingMethodId()).orElseThrow(
				() -> new RuntimeException("Không tìm thấy phương thức giao hàng ID: " + dto.getShippingMethodId()));
		order.setShippingMethods(sm);

		return order;
	}

}