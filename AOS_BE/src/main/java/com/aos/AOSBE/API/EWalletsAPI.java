package com.aos.AOSBE.API;

import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.PageRequest;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Service.*;
import com.aos.AOSBE.DTOS.*;
import com.aos.AOSBE.Mapper.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class EWalletsAPI {
	@Autowired
	private EWalletsService eWalletsService;
	
	@Autowired
	private EWalletsMapper eWalletsMapper;

	@GetMapping("/admin/EWallets")
	public ResponseEntity<List<EWalletsDTOS>> getAllEWalletsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<EWalletsDTOS> eWallets = new ArrayList<EWalletsDTOS>();
		eWalletsService.eWalletsFindAll(page, size).forEach(e -> {
			eWallets.add(eWalletsMapper.mapper(e));
		});
		return ResponseEntity.ok(eWallets);
	}

	@GetMapping("/admin/EWallets/{id}")
	public ResponseEntity<EWallets> getEWalletsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		EWallets eWallets =(EWallets)eWalletsService.eWalletsFindById(id).orElse(new EWallets());
		return ResponseEntity.ok(eWallets);
	}
	@PostMapping("/admin/EWallets")
	public ResponseEntity<EWallets> addNewEWallets(@RequestBody EWalletsDTOS entity) {
	    
	    EWallets saved = eWalletsService.eWalletsSave(eWalletsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/admin/EWallets")
	public ResponseEntity<?> updateEWallets(@RequestBody EWallets entity) {
			try {
			EWallets  isExist = eWalletsService.eWalletsFindById(id).orElse(null);
			if (isExist != null) {
				EWallets  update = eWalletsMapper.mapperToObject(entity);
				eWalletsService.eWalletsSave(update);
				return ResponseEntity.badRequest().body(Map.of("measage", "Update successfuly", "update", update));
			} else {
				return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(Map.of("measage", "Đã có lỗi xảy ra"));
		} 
	}
	@DeleteMapping("/admin/EWallets/{id}")
	public ResponseEntity<Void> deleteEWallets(@PathVariable int id) {
	    eWalletsService.eWalletsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
