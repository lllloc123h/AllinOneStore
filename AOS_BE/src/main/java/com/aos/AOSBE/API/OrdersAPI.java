package com.aos.AOSBE.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.aos.AOSBE.CommonFunctions.CommonKeyConstant;
import com.aos.AOSBE.DTOS.AccountsDTOS;
import com.aos.AOSBE.DTOS.MessageDTOS;
import com.aos.AOSBE.DTOS.OrderDetailResponseDTO;
import com.aos.AOSBE.DTOS.OrderItemDetailDTO;
import com.aos.AOSBE.DTOS.OrdersDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.EWallets;
import com.aos.AOSBE.Entity.Message;
import com.aos.AOSBE.Entity.OrderItems;
import com.aos.AOSBE.Entity.Orders;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Mapper.AccountsMapper;
import com.aos.AOSBE.Mapper.MessageMapper;
import com.aos.AOSBE.Mapper.OrderItemsMapper;
import com.aos.AOSBE.Mapper.OrdersMapper;
import com.aos.AOSBE.Service.AccountsService;
import com.aos.AOSBE.Service.EWalletsService;
import com.aos.AOSBE.Service.MessageService;
import com.aos.AOSBE.Service.OrderItemsService;
import com.aos.AOSBE.Service.OrdersService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class OrdersAPI {
	@Autowired
	private OrdersService ordersService;
	@Autowired
	private OrdersMapper ordersMapper;
	@Autowired
	private OrderItemsService orderItemsService;
	@Autowired
	private OrderItemsMapper orderItemsMapper;
	@Autowired
	private EWalletsService EWalletsservice;
	@Autowired
	private AccountsService accountService;
	@Autowired
	private AccountsMapper accountsMapper;

	@Autowired
	private MessageService messageService;
	@Autowired
	private MessageMapper messageMapper;
	private CommonKeyConstant commonKeyConstant = new CommonKeyConstant();

	@GetMapping("/admin/Orders")
	public ResponseEntity<?> getAllOrdersApi(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") Map<String, Object> filters) {
		filters.remove("page");
		filters.remove("size");
		Page<Orders> pageResult = ordersService.ordersFindAll(page, size, filters);
		List<OrdersDTOS> orders = pageResult.getContent().stream().map(ordersMapper::mapper)
				.collect(Collectors.toList());
		Map<String, Object> response = new HashMap<>();
		response.put("content", orders);
		response.put("totalPages", pageResult.getTotalPages());
		return ResponseEntity.ok(response);

	}

	@GetMapping("/admin/Orders/{id}")
	public ResponseEntity<Orders> getOrdersByIdApi(@PathVariable int id) {
		Orders orders = (Orders) ordersService.ordersFindById(id).orElse(new Orders());
		return ResponseEntity.ok(orders);
	}

	@PostMapping("/admin/Orders")
	public ResponseEntity<?> userAddNewOrders(@RequestBody OrdersDTOS entity) {
		try {
			Orders saved = ordersService.ordersSave(ordersMapper.mapperToObject(entity));

			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@PutMapping("/admin/Orders/{id}")
	public ResponseEntity<?> updateOrders(@PathVariable int id, @RequestBody OrdersDTOS entity) {
		try {
			Orders isExist = ordersService.ordersFindById(id).orElse(null);
			if (isExist != null) {
				Orders update = ordersMapper.mapperToObject(entity);
				ordersService.ordersSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		}
	}

	@PostMapping("/user/Orders")
	public ResponseEntity<?> addNewOrdersByUserRoles(@RequestBody OrdersDTOS entity) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts user = accountService.accountsFindByEmail(userEmail).orElse(null);
			entity.setAccounts(user.getId());

			MessageDTOS entityMessage = new MessageDTOS();
			entityMessage.setKeyMessage(commonKeyConstant.MessageOrder);
			entityMessage.setAccounts(userEmail);
			Message messageSaved = messageService.messageSave(messageMapper.mapperToObject(entityMessage));
			// Lưu đơn hàng trước
			Orders saved = ordersService.ordersSave(ordersMapper.mapperToObject(entity));

			// Mapping các item
			List<OrderItems> orderItems = entity.getProducts().stream().map(item -> {
				OrderItems orderItem = orderItemsMapper.mapperToObject(item);
				orderItem.setOrders(saved);
				return orderItem;
			}).collect(Collectors.toList());

			// Lưu các item
			orderItemsService.orderItemsSaveAll(orderItems);

			return ResponseEntity.ok(saved);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
	}

	@GetMapping("/user/Orders/paypending")
	public ResponseEntity<?> addNewOrdersByUserRolesWithKey(@RequestParam("KEY") String key) {
		try {
			String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			Accounts user = accountService.accountsFindByEmail(userEmail).orElse(null);
			List<OrdersDTOS> listUserOrders = ordersService.ordersFindByAccountAndKeyPaymentPending(user.getId(), key)
					.stream().map(ordersMapper::mapper).collect(Collectors.toList());
			;
			return ResponseEntity.ok(listUserOrders);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("message", "Đã có lỗi xảy ra"));
		}
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
		OrdersDTOS orderDTO = ordersMapper.mapper(order); // thông tin đơn hàng

		// Lấy thông tin account
		Accounts account = order.getAccounts();
		AccountsDTOS accountDTO = accountsMapper.mapper(account); // ✔️ Dùng mapper đã có

		// Lấy danh sách sản phẩm
		List<OrderItems> items = orderItemsService.findByOrderId(id);
		List<OrderItemDetailDTO> itemsDTO = new ArrayList<>();

		for (OrderItems item : items) {
			ProductItems pi = item.getProductItems();
			String productName = (pi.getBaseProducts() != null) ? pi.getBaseProducts().getName() : "N/A";

			itemsDTO.add(new OrderItemDetailDTO(item.getQty(), item.getSellingPrice(), item.getTotal(), item.isGift(),
					pi.getSku(), productName, pi.getDescription()));
		}

		// Trả về full response
		OrderDetailResponseDTO response = new OrderDetailResponseDTO(orderDTO, itemsDTO, accountDTO);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/Users/Orders/cancelRefundOrder/{id}")
	public ResponseEntity<?> cancelRefundOrder(@PathVariable int id) {
		try {
			Orders OrderCancel = ordersService.ordersFindById(id).orElse(null);
			if (OrderCancel != null) {
				if (OrderCancel.getShippingStatus().equals("Đang xử lý")) {
					if (OrderCancel.getPaymentStatus().equals("Đã thanh toán")) {
						Accounts ac = OrderCancel.getAccounts();
						EWallets ew = EWalletsservice.eWalletsFindByAccountEmail(ac.getEmail()).orElse(null);
						if (ew != null) {
							ew.setBalance(ew.getBalance() + OrderCancel.getFinalTotal());
							EWalletsservice.eWalletsSave(ew);
							Accounts admin = accountService.accountsFindById(1).orElse(null);
							if (admin != null) {
								EWallets ewadmin = EWalletsservice.eWalletsFindByAccountEmail(admin.getEmail())
										.orElse(null);
								ewadmin.setBalance(ewadmin.getBalance() - OrderCancel.getFinalTotal());
								EWalletsservice.eWalletsSave(ewadmin);
								OrderCancel.setShippingStatus("Cancel");
								ordersService.ordersSave(OrderCancel);
							} else {
								return ResponseEntity.ok(Map.of("MESSAGE", "admin không tồn tại"));
							}
							return ResponseEntity.ok(Map.of("MESSAGE", "Hủy đơn hoàn tiền thành công"));
						} else {
							return ResponseEntity.ok(
									Map.of("MESSAGE", "người dùng chưa có tài khoản Ewallet, vui lòng tạo tài khoản"));
						}

					} else {
						OrderCancel.setShippingStatus("Cancel");
						ordersService.ordersSave(OrderCancel);
						return ResponseEntity.ok(Map.of("MESSAGE", "hủy thành công"));
					}
				} else {
					return ResponseEntity.ok()
							.body(Map.of("MESSAGE", "Đơn hàng đã được vân chuyển, không thể hủy đơn"));
				}
			} else {
				return ResponseEntity.ok().body(Map.of("MESSAGE", "không tìm thấy đơn hàng "));
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of("MESSAGE", "ĐÃ CÓ LỖI XẢY RA " + e.getMessage()));
		}
	}

	@PutMapping("/admin/Orders/update-ghn-status/{id}")
	public ResponseEntity<?> updateShippingStatusFromGHN(@PathVariable int id) {
		try {
			Orders updatedOrder = ordersService.updateShippingStatusFromGHN(id);
			return ResponseEntity.ok(Map.of("message", "Cập nhật trạng thái vận đơn thành công", "shippingStatus",
					updatedOrder.getShippingStatus()));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(Map.of("error", "Lỗi hệ thống: " + e.getMessage()));
		}
	}
}