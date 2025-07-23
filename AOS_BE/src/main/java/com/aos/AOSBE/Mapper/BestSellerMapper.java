//package com.aos.AOSBE.Mapper;
//
//import com.aos.AOSBE.DTOS.BestSellerProductDTO;
//import org.springframework.stereotype.Component;
//
//@Component
//public class BestSellerMapper {
//    public BestSellerProductDTO mapToDTO(Object[] row) {
//        return new BestSellerProductDTO(
//            (Integer) row[0],     // productItemId
//            (Integer) row[1],     // baseProductId
//            (String) row[2],      // name
//            (String) row[3],      // material
//            (String) row[4],      // imageUrl
//            ((Number) row[5]).doubleValue(), // price
//            (Integer) row[6],     // qty
//            (Integer) row[7]      // turnBuy
//        );
//    }
//}