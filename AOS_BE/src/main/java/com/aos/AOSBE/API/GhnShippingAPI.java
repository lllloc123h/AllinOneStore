package com.aos.AOSBE.API;

import com.aos.AOSBE.DTOS.GhnShippingFeeDTO;
import com.aos.AOSBE.Service.GhnShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/shipping")
@CrossOrigin(origins = "*") 
public class GhnShippingAPI {

    @Autowired
    private GhnShippingService ghnShippingService;

    @PostMapping("/fee")
    public ResponseEntity<?> calculateAutoFee(@RequestBody GhnShippingFeeDTO body) {
        try {
            Map<String, Object> fee = ghnShippingService.calculateShippingFee(
                body.getTo_district_id(),
                body.getTo_ward_code()
            );
            return ResponseEntity.ok(fee);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/shop")
    public ResponseEntity<?> getShopAddress() {
        return ResponseEntity.ok(ghnShippingService.getShopAddressFromGHN());
    }
    
    @GetMapping("/services")
    public ResponseEntity<?> getAvailableServices(@RequestParam int toDistrictId) {
        return ResponseEntity.ok(ghnShippingService.getAvailableServices(toDistrictId));
    }

}
