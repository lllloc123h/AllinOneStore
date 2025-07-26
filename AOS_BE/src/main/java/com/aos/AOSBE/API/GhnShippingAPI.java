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
    public ResponseEntity<?> calculateFee(@RequestBody GhnShippingFeeDTO body) {
        Map<String, Object> fee = ghnShippingService.calculateShippingFee(
            body.getService_id(),
            body.getTo_district_id(),
            body.getTo_ward_code()
        );
        return ResponseEntity.ok(fee);
    }

    @GetMapping("/shop")
    public ResponseEntity<?> getShopAddress() {
        return ResponseEntity.ok(ghnShippingService.getShopAddressFromGHN());
    }
}
