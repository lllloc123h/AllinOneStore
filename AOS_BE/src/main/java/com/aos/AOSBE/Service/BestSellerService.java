package com.aos.AOSBE.Service;

import com.aos.AOSBE.DTOS.BestSellerProductDTO;
import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Repository.ProductItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BestSellerService {

    private final ProductItemsRepository productItemsRepository;

    public List<BestSellerProductDTO> getBestSellers(int limit) {
        return productItemsRepository.findBestSellersWithTotalTurnBuy(PageRequest.of(0, limit));
    }

}
