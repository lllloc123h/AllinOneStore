package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.List;

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

import com.aos.AOSBE.DTOS.OrdersDTOS;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Mapper.OrdersMapper;
import com.aos.AOSBE.Service.OrdersService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdersAPI {
	@Autowired
	private OrdersService ordersService;

	@Autowired
	private OrdersMapper ordersMapper;

	@GetMapping("/admin/Orders")
	public ResponseEntity<List<OrdersDTOS>> getAllOrdersApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {

		List<OrdersDTOS> orders = new ArrayList<OrdersDTOS>();
		ordersService.ordersFindAll(page, size).forEach(e -> {
			orders.add(ordersMapper.mapper(e));
		});
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/admin/Orders/{id}")
	public ResponseEntity<Orders> getOrdersByIdApi(@PathVariable int id) {
		// try{
		// }catch(Exception e){
		// }

		Orders orders = (Orders) ordersService.ordersFindById(id).orElse(new Orders());
		return ResponseEntity.ok(orders);
	}

	@PostMapping("/admin/Orders")
	public ResponseEntity<Orders> addNewOrders(@RequestBody OrdersDTOS entity) {

		Orders saved = ordersService.ordersSave(ordersMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PostMapping("/Orders")
	public ResponseEntity<Orders> addNewOrdersByUser(@RequestBody OrdersDTOS entity) {
		Orders saved = ordersService.ordersSave(ordersMapper.mapperToObject(entity));
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/admin/Orders")
	public ResponseEntity<Orders> updateOrders(@RequestBody Orders entity) {
		Orders updated = ordersService.ordersSave(entity);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/admin/Orders/{id}")
	public ResponseEntity<Void> deleteOrders(@PathVariable int id) {
		ordersService.ordersDeleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/Orders/detail/{id}")
public ResponseEntity<?> getOrderDetail(@PathVariable int id) {
    Optional<Orders> orderOpt = ordersService.ordersFindById(id);
    if (orderOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Orders order = orderOpt.get();
    OrdersDTOS orderDTO = ordersMapper.mapper(order);

    List<OrderItems> items = orderItemsService.findByOrderId(id);
    List<OrderItemDetailDTO> itemsDTO = new ArrayList<>();

    for (OrderItems item : items) {
        ProductItems pi = item.getProductItems();
        String productName = (pi.getBaseProducts() != null) ? pi.getBaseProducts().getName() : "N/A";

        itemsDTO.add(new OrderItemDetailDTO(
            item.getQty(),
            item.getSellingPrice(),
            item.getTotal(),
            item.isGift(),
            pi.getSku(),
            productName,
            pi.getDescription()
        ));
    }

    OrderDetailResponseDTO response = new OrderDetailResponseDTO(orderDTO, itemsDTO);
    return ResponseEntity.ok(response);
}

}
