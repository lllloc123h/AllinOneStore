package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.aos.AOSBE.DTOS.GhnDTO;
import com.aos.AOSBE.DTOS.OrderItemsDTOS;
import com.aos.AOSBE.Entity.OrderItems;
import com.aos.AOSBE.Mapper.OrderItemsMapper;
import com.aos.AOSBE.Service.OrderItemsService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
@Log4j2
public class OrderItemsAPI {
	@Autowired
	private OrderItemsService orderItemsService;

	@Autowired
	private OrderItemsMapper orderItemsMapper;

	@PostMapping("/webhook/status")
	public ResponseEntity<String> getStatus(@RequestBody GhnDTO entity) {
		System.out.println(entity);
		return ResponseEntity.ok("Success");
	}

	@GetMapping("/admin/OrderItems")
	public ResponseEntity<?> getAllOrderItemsApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<OrderItems> pageResult = orderItemsService.orderItemsFindAll(page, size, filters);
		List<OrderItemsDTOS> orderItems = pageResult.getContent().stream().map(orderItemsMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", orderItems);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

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