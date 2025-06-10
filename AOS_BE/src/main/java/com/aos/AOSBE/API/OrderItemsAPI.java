package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aos.AOSBE.DTOS.OrderItemsDTOS;
import com.aos.AOSBE.Entity.OrderItems;
import com.aos.AOSBE.Mapper.OrderItemsMapper;
import com.aos.AOSBE.Service.OrderItemsService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
@Log4j2
public class OrderItemsAPI {
	@Autowired
	private OrderItemsService orderItemsService;

	@Autowired
	private OrderItemsMapper orderItemsMapper;

	@GetMapping("/admin/OrderItems")
	public ResponseEntity<List<OrderItemsDTOS>> getAllOrderItemsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<OrderItemsDTOS> orderItems = new ArrayList<OrderItemsDTOS>();
		orderItemsService.orderItemsFindAll(page, size).forEach(e -> {
			orderItems.add(orderItemsMapper.mapper(e));
		});
		return ResponseEntity.ok(orderItems);
	}

	@GetMapping("/admin/OrderItems/{id}")
	public ResponseEntity<OrderItems> getOrderItemsByIdApi(@PathVariable int id) {
		OrderItems orderItems = (OrderItems) orderItemsService.orderItemsFindById(id).orElse(new OrderItems());
		return ResponseEntity.ok(orderItems);
	}

	@PostMapping("/admin/OrderItems")
	public ResponseEntity<OrderItems> addNewOrderItems(@RequestBody OrderItemsDTOS entity) {

		OrderItems saved = orderItemsService.orderItemsSave(orderItemsMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PostMapping("/OrderItems")
	public ResponseEntity<?> addNewOrderItems(@RequestBody List<OrderItemsDTOS> entity) {
		try {
			List<OrderItems> OrderToOrderItem = new ArrayList<>();
			entity.forEach(element -> {
				OrderToOrderItem.add(orderItemsMapper.mapperToObject(element));
			});
			log.info(OrderToOrderItem);
			orderItemsService.orderItemsSaveAll(OrderToOrderItem);
			return ResponseEntity.ok(Map.of("measage", "Order successfully", "OrderItems", OrderToOrderItem));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}

	}

	@PutMapping("/admin/OrderItems")
	public ResponseEntity<OrderItems> updateOrderItems(@RequestBody OrderItems entity) {
		OrderItems updated = orderItemsService.orderItemsSave(entity);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/admin/OrderItems/{id}")
	public ResponseEntity<Void> deleteOrderItems(@PathVariable int id) {
		orderItemsService.orderItemsDeleteById(id);
		return ResponseEntity.noContent().build();
	}

}
