package com.aos.AOSBE.API;

import com.aos.AOSBE.DTOS.BestSellerProductDTO;
import com.aos.AOSBE.Service.BestSellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomePageAPI {

    private final BestSellerService bestSellerService;

    @GetMapping("/bestsellers")
    public ResponseEntity<List<BestSellerProductDTO>> getBestSellers(@RequestParam(defaultValue = "8") int limit) {
        return ResponseEntity.ok(bestSellerService.getBestSellers(limit));
    }
}
