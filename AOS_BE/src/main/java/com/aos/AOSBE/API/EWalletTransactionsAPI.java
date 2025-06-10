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
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class EWalletTransactionsAPI {
	@Autowired
	private EWalletTransactionsService eWalletTransactionsService;
	
	@Autowired
	private EWalletTransactionsMapper eWalletTransactionsMapper;

	@GetMapping("/EWalletTransactions")
	public ResponseEntity<List<EWalletTransactionsDTOS>> getAllEWalletTransactionsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<EWalletTransactionsDTOS> eWalletTransactions = new ArrayList<EWalletTransactionsDTOS>();
		eWalletTransactionsService.eWalletTransactionsFindAll(page, size).forEach(e -> {
			eWalletTransactions.add(eWalletTransactionsMapper.mapper(e));
		});
		return ResponseEntity.ok(eWalletTransactions);
	}

	@GetMapping("/EWalletTransactions/{id}")
	public ResponseEntity<EWalletTransactions> getEWalletTransactionsByIdApi(@PathVariable int id) {
		//try{
		//}catch(Exception e){
		//}
		
		EWalletTransactions eWalletTransactions =(EWalletTransactions)eWalletTransactionsService.eWalletTransactionsFindById(id).orElse(new EWalletTransactions());
		return ResponseEntity.ok(eWalletTransactions);
	}
	@PostMapping("/EWalletTransactions")
	public ResponseEntity<EWalletTransactions> addNewEWalletTransactions(@RequestBody EWalletTransactionsDTOS entity) {
	    
	    EWalletTransactions saved = eWalletTransactionsService.eWalletTransactionsSave(eWalletTransactionsMapper.mapperToObject(entity));	    
	    return ResponseEntity.ok(saved);
	}
	@PutMapping("/EWalletTransactions")
	public ResponseEntity<EWalletTransactions> updateEWalletTransactions(@RequestBody EWalletTransactions entity) {
	    EWalletTransactions updated = eWalletTransactionsService.eWalletTransactionsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/EWalletTransactions/{id}")
	public ResponseEntity<Void> deleteEWalletTransactions(@PathVariable int id) {
	    eWalletTransactionsService.eWalletTransactionsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
