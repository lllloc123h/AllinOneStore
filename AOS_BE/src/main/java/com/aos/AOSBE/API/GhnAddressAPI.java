package com.aos.AOSBE.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.aos.AOSBE.Service.GhnAddressService;

@RestController
@RequestMapping("/api/ghn")
public class GhnAddressAPI {

    @Autowired
    private GhnAddressService ghnAddressService;

    // Lấy danh sách tỉnh/thành phố
    @GetMapping("/provinces")
    public ResponseEntity<?> getProvinces() {
        return ResponseEntity.ok(ghnAddressService.getProvinces());
    }

    // Lấy danh sách quận/huyện theo province_id
    @GetMapping("/districts")
    public ResponseEntity<?> getDistricts(@RequestParam("province_id") int provinceId) {
        return ResponseEntity.ok(ghnAddressService.getDistricts(provinceId));
    }

    // Lấy danh sách phường/xã theo district_id
    @GetMapping("/wards")
    public ResponseEntity<?> getWards(@RequestParam("district_id") int districtId) {
        return ResponseEntity.ok(ghnAddressService.getWards(districtId));
    }
}
