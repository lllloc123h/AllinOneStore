package com.aos.AOSBE.API;

import com.aos.AOSBE.DTOS.PromotionProductsDTOS;
import com.aos.AOSBE.DTOS.PromotionsDTOS;
import com.aos.AOSBE.Entity.PromotionProducts;
import com.aos.AOSBE.Entity.Promotions;
import com.aos.AOSBE.Mapper.PromotionProductsMapper;
import com.aos.AOSBE.Service.PromotionProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class PromotionProductsAPI {
    @Autowired
    private PromotionProductsService promotionProductsService;
    @Autowired
    private PromotionProductsMapper promotionProductsMapper;

    @GetMapping("/admin/PromotionProducts")
    public ResponseEntity<?> getAllPromotionProductsApi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "0") Map<String, Object> filters) {
        filters.remove("page");
        filters.remove("size");
        Page<PromotionProducts> pageResult = promotionProductsService.findAll(page, size, filters);
        List<PromotionProductsDTOS> promotions = pageResult.getContent().stream().map(promotionProductsMapper::mapper)
                .collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("content", promotions);
        response.put("totalPages", pageResult.getTotalPages());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/PromotionProducts/{id}")
    public ResponseEntity<?> getPromotionProductsById(@PathVariable int id) {
        PromotionProducts promotionProduct = promotionProductsService.findById(id).orElse(new PromotionProducts());
        return ResponseEntity.ok(promotionProductsMapper.mapper(promotionProduct));

    }
    @PostMapping("/admin/PromotionProducts")
    public ResponseEntity<?> addNewPromotions(@RequestBody PromotionProductsDTOS entity) {
        System.out.println("PromotionProducts: " + entity);
        PromotionProducts saved = promotionProductsService.save(promotionProductsMapper.mapperToObject(entity));
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/admin/PromotionProducts/{id}")
    public ResponseEntity<?> updatePromotions(@PathVariable int id, @RequestBody PromotionProductsDTOS entity) {
        try {
            PromotionProducts isExist = promotionProductsService.findById(id).orElse(null);
            if (isExist != null) {
                PromotionProducts update = promotionProductsMapper.mapperToObject(entity);
                promotionProductsService.save(update);
                return ResponseEntity.ok().body(Map.of("measage", "Update successfuly", "update", update));
            } else {
                return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
        }
    }

    @DeleteMapping("/admin/PromotionProducts/{id}")
    public ResponseEntity<Void> deletePromotions(@PathVariable int id) {
        promotionProductsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


