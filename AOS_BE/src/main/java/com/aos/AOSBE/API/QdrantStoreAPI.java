package com.aos.AOSBE.API;

import com.aos.AOSBE.Entity.ProductItems;
import com.aos.AOSBE.Service.ProductItemsService;
import com.aos.AOSBE.Service.QdrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class QdrantStoreAPI {
    @Autowired
    private ProductItemsService productItemsService;
    @Autowired
    private QdrantService qdrantService;
    @PutMapping("/admin/qdrant")
    public ResponseEntity<?> upsertData(@RequestParam("productItemId") int productItemId) {
      try {
         ProductItems productItems = productItemsService.productItemsFindById(productItemId).get();
        qdrantService.upsertDocument(productItems);
        return ResponseEntity.ok("Upsert to Qdrant successfully");
      } catch (Exception e) {
        return ResponseEntity.status(500).body("Error upserting to Qdrant: " + e.getMessage());
      }
    }
}
