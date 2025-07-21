package com.aos.AOSBE.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aos.AOSBE.DTOS.BestSellerProductDTO;
import com.aos.AOSBE.Repository.ProductItemsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BestSellerService {

    private final ProductItemsRepository productItemsRepository;

    public List<BestSellerProductDTO> getBestSellers(int limit) {
        List<Object[]> results = productItemsRepository.findBestSellerProductItems(limit);
        List<BestSellerProductDTO> dtos = new ArrayList<>();

        for (Object[] obj : results) {
            BestSellerProductDTO dto = new BestSellerProductDTO();
            dto.setProductItemId((int) obj[0]);
            dto.setBaseProductId((int) obj[1]);
            dto.setName((String) obj[2]);
            dto.setMaterial((String) obj[3]);
            dto.setImageUrl((String) obj[4]);
            dto.setPrice(obj[5] != null ? ((Number) obj[5]).doubleValue() : 0);
            dto.setQty((int) obj[6]);
            dto.setTurnBuy((int) obj[7]);

            dtos.add(dto);
        }

        return dtos;
    }
}
