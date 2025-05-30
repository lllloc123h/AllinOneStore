package com.aos.AOSBE.API;

import java.util.List;

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
public class TraceXeEventMapAPI {
	@Autowired
	private TraceXeEventMapService traceXeEventMapService;
	private TraceXeEventMapMapper traceXeEventMapMapper=new TraceXeEventMapMapper();

	@GetMapping("/TraceXeEventMap")
	public ResponseEntity<List<TraceXeEventMapDTOS>> getAllTraceXeEventMapApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<TraceXeEventMapDTOS> traceXeEventMap = new ArrayList<TraceXeEventMapDTOS>();
		traceXeEventMapService.traceXeEventMapFindAll(page, size).forEach(e -> {
			traceXeEventMap.add(traceXeEventMapMapper.mapper(e));
		});
		return ResponseEntity.ok(traceXeEventMap);
	}

	@GetMapping("/TraceXeEventMap/{id}")
	public ResponseEntity<TraceXeEventMap> getTraceXeEventMapByIdApi(@PathVariable int id) {
		TraceXeEventMap traceXeEventMap =(TraceXeEventMap)traceXeEventMapService.traceXeEventMapFindById(id).orElse(new TraceXeEventMap());
		return ResponseEntity.ok(traceXeEventMap);
	}
	@PostMapping("/TraceXeEventMap")
	public ResponseEntity<TraceXeEventMap> addNewTraceXeEventMap(@RequestBody TraceXeEventMap entity) {
	    TraceXeEventMap saved = traceXeEventMapService.traceXeEventMapSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/TraceXeEventMap")
	public ResponseEntity<TraceXeEventMap> updateTraceXeEventMap(@RequestBody TraceXeEventMap entity) {
	    TraceXeEventMap updated = traceXeEventMapService.traceXeEventMapSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/TraceXeEventMap/{id}")
	public ResponseEntity<Void> deleteTraceXeEventMap(@PathVariable int id) {
	    traceXeEventMapService.traceXeEventMapDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
