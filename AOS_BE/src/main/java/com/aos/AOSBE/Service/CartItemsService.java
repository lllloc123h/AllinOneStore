package com.aos.AOSBE.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.DTOS.CreateComboDTO;
import com.aos.AOSBE.DTOS.OrderItemsDTOS;
import com.aos.AOSBE.Entity.Accounts;
import com.aos.AOSBE.Entity.CartItems;
import com.aos.AOSBE.Repository.CartItemsRepository;
import com.aos.AOSBE.Repository.ProductItemsRepository;
import com.aos.AOSBE.Repository.PromotionProductsRepository;
import com.aos.AOSBE.Repository.PromotionsRepository;

@Service
public class CartItemsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private CartItemsRepository cartItemsRepository;
	@Autowired
	private ProductItemsRepository productItemsRepository;
	@Autowired
	private PromotionsRepository promotionsRepository;
	@Autowired
	private PromotionProductsRepository promotionProductsRepository;
    @Autowired
    private PromotionsService promotionService;


	public Page<CartItems> cartItemsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<CartItems> spec = specBuilder.buildFilter(filters);
		return cartItemsRepository.findAll(spec, pageable);
	}

	public List<CartItems> cartItemsFindAccounts(String email) {
		return cartItemsRepository.findByAccountsEmail(email);
	}

	@Transactional
	public CartItems cartItemsSave(CartItems cartItems) {
		return cartItemsRepository.save(cartItems);
	}

	@Transactional
	public void cartItemsDeleteAll(String email, List<OrderItemsDTOS> cartItemsSelected) {
	    List<CartItems> listCartNeedToRemove = new ArrayList<>();
	    for (OrderItemsDTOS element : cartItemsSelected) {
	        CartItems temp = cartItemsRepository
	            .findByAccountsEmailAndProductItemsIdAndComboGroupId(
	                email,
	                element.getProductItemId(),
	                element.getComboGroupId() // nhớ truyền từ DTO xuống
	            )
	            .orElse(null);
	        if (temp != null) {
	            listCartNeedToRemove.add(temp);
	        }
	    }
	    cartItemsRepository.deleteAll(listCartNeedToRemove);
	}

	public Optional<CartItems> cartItemsFindById(int id) {
		return cartItemsRepository.findById(id);
	}

	@Transactional
	public void cartItemsDeleteById(int id) {
		cartItemsRepository.deleteById(id);
	}

	// ✅ TH1: tìm cart item theo email + product (KHÔNG COMBO)
    public CartItems cartFindByAccountEmailAndProductItemId(String email, int productItemId) {
        return cartItemsRepository
                .findByAccountsEmailAndProductItemsIdAndNoCombo(email, productItemId)
                .orElse(null);
    }

    // ✅ TH2: tìm cart item theo email + product + comboGroupId
    public CartItems cartFindByAccountEmailAndProductItemId(String email, int productItemId, UUID comboGroupId) {
        return cartItemsRepository
                .findByAccountsEmailAndProductItemsIdAndComboGroupId(email, productItemId, comboGroupId)
                .orElse(null);
    }

    @Transactional
    public void addCombo(CreateComboDTO entity, Accounts account) {
        // Nếu đang convert từ item lẻ -> xóa item đó
        if (entity.getCartId() != null) {
            cartItemsRepository.deleteById(entity.getCartId());
        }

        UUID existingGroupId = null;

        // Tìm trong giỏ của user xem đã có combo giống hệt chưa
        List<CartItems> userCart = cartItemsRepository.findAllByAccountsId(account.getId());

        outerLoop:
        for (CartItems ci : userCart) {
            if (ci.getComboGroupId() != null) {
                UUID groupId = ci.getComboGroupId();
                List<CartItems> comboItems = cartItemsRepository.findAllByComboGroupId(groupId);

                if (comboItems.size() == entity.getItems().size()) {
                    boolean same = true;
                    for (CreateComboDTO.Items dtoItem : entity.getItems()) {
                        CartItems matched = comboItems.stream()
                            .filter(c -> c.getProductItems().getId().equals(dtoItem.getItemId()))
                            .findFirst().orElse(null);
                        if (matched == null || (matched.getQty() / matched.getComboQty()) != dtoItem.getQuantity()) {
                            same = false;
                            break;
                        }
                    }
                    if (same) {
                        existingGroupId = groupId;
                        break outerLoop;
                    }
                }
            }
        }

        if (existingGroupId != null) {
            // Combo đã tồn tại -> tăng comboQty
            List<CartItems> comboItems = cartItemsRepository.findAllByComboGroupId(existingGroupId);
            for (int i = 0; i < comboItems.size(); i++) {
                CartItems ci = comboItems.get(i);
                int addQty = entity.getItems().get(i).getQuantity();
                ci.setComboQty(ci.getComboQty() + 1);
                ci.setQty(ci.getQty() + addQty);
                cartItemsRepository.save(ci);
            }
        } else {
            // Tạo combo mới
            UUID newGroupId = UUID.randomUUID();
            for (CreateComboDTO.Items item : entity.getItems()) {
                CartItems cartItem = new CartItems();
                cartItem.setAccounts(account);
                cartItem.setComboGroupId(newGroupId);
                cartItem.setQty(item.getQuantity());
                cartItem.setComboQty(1);
                cartItem.setIsGift(
                    promotionProductsRepository.findByProductItemsIdAndPromotionsId(item.getItemId(), item.getPromotionId()).isGift()
                );
                cartItem.setProductItems(productItemsRepository.findById(item.getItemId()).orElse(null));
                cartItem.setPromotions(promotionsRepository.findById(item.getPromotionId()).orElse(null));
                cartItemsRepository.save(cartItem);
            }
        }
    }


	public List<CartItems> findCartItemsByAccountsAndComboGroupId(Accounts accounts, UUID comboGroup) {
		return cartItemsRepository.findCartItemsByAccountsAndComboGroupId(accounts, comboGroup);
	}

	public void deleteCombo(Accounts account, UUID comboGroupId) {
		List<CartItems> cartItems = cartItemsRepository.findCartItemsByAccountsAndComboGroupId(account, comboGroupId);
		if (cartItems.size() > 0) {
			for (CartItems item : cartItems) {
				cartItemsRepository.delete(item);
			}
		}
	}
	@Transactional
	public void updateCartItemsWherePromotionIsNotExist(int promotionId) {
		cartItemsRepository.updateCartItemsWherePromotionIsNotExist(promotionId);
	}

	@Transactional
	public void validateCartItemsPromotions(Accounts account) {
	    List<CartItems> cartItems = cartItemsRepository.findByAccountsEmail(account.getEmail());

	    for (CartItems item : cartItems) {
	        if (item.getPromotions() != null) {
	            boolean expired = item.getPromotions().getEndAt().isBefore(java.time.LocalDateTime.now());
	            boolean inactive = !item.getPromotions().isActive();
	            boolean outOfStock = item.getPromotions().getQty() <= 0;

	            if (expired || inactive || outOfStock) {
	                // reset thành sản phẩm bình thường
	                item.setPromotions(null);
	                item.setIsGift(false);
	                cartItemsRepository.save(item);
	            }
	        }
	    }
	}
	
	public List<CartItems> getValidatedCart(String email) {
	    List<CartItems> cartItems = cartItemsRepository.findByAccountsEmail(email);

	    for (CartItems item : cartItems) {
	        if (item.getPromotions() != null) {
	            boolean valid = promotionService.isPromotionValid(item.getPromotions());
	            if (!valid) {
	                item.setPromotions(null);
	                item.setIsGift(false);
	                cartItemsRepository.save(item); // lưu từng item
	            }
	        }
	    }
	    return cartItems;
	}

}