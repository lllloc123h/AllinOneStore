package com.aos.AOSBE.Mapper;

import com.aos.AOSBE.CommonFunctions.HandleListSkuToFilter;
import com.aos.AOSBE.DTOS.CustomDTOS;
import com.aos.AOSBE.DTOS.CustomsDTOS;
import com.aos.AOSBE.DTOS.OrderSummaryDTOS;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.CustomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class OrderSummaryMapper {
@Autowired
private HandleListSkuToFilter handleListSkuToFilter;
@Autowired
private CustomsRepository customsRepository;
@Autowired
private CustomsMapper customsMapper;
    public OrderSummaryDTOS toDTO(Orders order) {
        OrderSummaryDTOS dto = new OrderSummaryDTOS();

        dto.setId(order.getId());
        dto.setShippingStatus(order.getShippingStatus());
        dto.setEstimatedShippingFee(order.getEstimatedShippingFee());
        dto.setFreeshipCouponCode(order.getFreeshipCouponCode());
        dto.setActualShippingFee(order.getActualShippingFee());
        dto.setDiscountCouponCode(order.getDiscountCouponCode());
        dto.setDiscountValue(order.getDiscountValue());
        dto.setShippedDate(order.getShippedDate());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setNote(order.getNote());
        dto.setPoint(order.getPoint());
        dto.setFinalTotal(order.getFinalTotal());
        dto.setOrderInfor(order.getOrderInfor());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setUpdatedAt(order.getUpdatedAt());
        dto.setAccounts(order.getAccounts().getId());
        dto.setPaymentMethodId(order.getPaymentMethods().getId());
        dto.setPaymentMethodName(order.getPaymentMethods().getName());
        dto.setShippingMethodId(order.getShippingMethods().getId());
        dto.setShippingMethodName(order.getShippingMethods().getName());
        dto.setOrderCode(order.getGhnOrderCode());

        List<OrderSummaryDTOS.OrderItemDTO> itemDTOs = order.getOrderItems().stream()
            .map(this::toItemDTO)
            .collect(Collectors.toList());
List<CustomsDTOS> customs = customsRepository.findCustomByOrderId(order.getId()).stream()
        .map(customsMapper::mapper)
        .collect(Collectors.toList());
        dto.setCustoms(customs);
        // Nếu DTO có field items, bạn cần thêm:
        dto.setItems(itemDTOs);
        return dto;
    }

    public OrderSummaryDTOS.OrderItemDTO toItemDTO(OrderItems item) {
        ProductItems product = item.getProductItems();
        Promotions promotion = item.getPromotions();

        OrderSummaryDTOS.OrderItemDTO dto = new OrderSummaryDTOS.OrderItemDTO();

        dto.setId(item.getId());
        dto.setQty(item.getQty());
        dto.setPriceAtBuy(item.getPriceAtBuy());
        dto.setCostAtBuy(item.getCostAtBuy());
        dto.setIsGift(item.getIsGift());
        dto.setSellingPrice(item.getSellingPrice());
        dto.setTotal(item.getTotal());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setUpdatedAt(item.getUpdatedAt());
        dto.setOrders(item.getOrders().getId());
        dto.setProductItems(product.getId());
        dto.setPromotions(promotion != null ? promotion.getId() : 0);
        dto.setPromotionName(promotion != null ? promotion.getName() : null);
        dto.setComboGroup(item.getComboGroup());
        dto.setComboGroupId(item.getComboGroupId());
        dto.setComboQty(item.getComboQty());
        dto.setProductItemId(product.getId());
        dto.setName(product.getBaseProducts().getName());
        dto.setMain_image_url(product.getBaseProducts().getMainImageUrl());
        dto.setPrice(product.getPrice());

        // Thêm ProductDTO
        OrderSummaryDTOS.ProductDTO productDTO = new OrderSummaryDTOS.ProductDTO();
        productDTO.setProductId(product.getId());
        productDTO.setSku(handleListSkuToFilter.getDescriptionOfSku(product.getSku()));
        productDTO.setProductName(product.getBaseProducts().getName());
        productDTO.setImageUrl(product.getBaseProducts().getMainImageUrl());
        dto.setProduct(productDTO);

        return dto;
    }
}