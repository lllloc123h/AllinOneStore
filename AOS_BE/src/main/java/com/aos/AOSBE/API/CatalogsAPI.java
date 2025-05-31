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
@RequestMapping("/Api/Admin")
@CrossOrigin(origins = "http://localhost:5173")
public class CatalogsAPI {
	@Autowired
	private CatalogsService catalogsService;
	private CatalogsMapper catalogsMapper=new CatalogsMapper();

	@GetMapping("/Catalogs")
	public ResponseEntity<List<CatalogsDTOS>> getAllCatalogsApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<CatalogsDTOS> catalogs = new ArrayList<CatalogsDTOS>();
		catalogsService.catalogsFindAll(page, size).forEach(e -> {
			catalogs.add(catalogsMapper.mapper(e));
		});
		return ResponseEntity.ok(catalogs);
	}

	@GetMapping("/Catalogs/{id}")
	public ResponseEntity<Catalogs> getCatalogsByIdApi(@PathVariable int id) {
		Catalogs catalogs =(Catalogs)catalogsService.catalogsFindById(id).orElse(new Catalogs());
		return ResponseEntity.ok(catalogs);
	}
	@PostMapping("/Catalogs")
	public ResponseEntity<Catalogs> addNewCatalogs(@RequestBody Catalogs entity) {
	    Catalogs saved = catalogsService.catalogsSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/Catalogs")
	public ResponseEntity<Catalogs> updateCatalogs(@RequestBody Catalogs entity) {
	    Catalogs updated = catalogsService.catalogsSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/Catalogs/{id}")
	public ResponseEntity<Void> deleteCatalogs(@PathVariable int id) {
	    catalogsService.catalogsDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
