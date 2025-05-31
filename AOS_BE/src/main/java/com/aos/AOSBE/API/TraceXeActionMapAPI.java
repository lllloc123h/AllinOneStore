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
public class TraceXeActionMapAPI {
	@Autowired
	private TraceXeActionMapService traceXeActionMapService;
	private TraceXeActionMapMapper traceXeActionMapMapper=new TraceXeActionMapMapper();

	@GetMapping("/TraceXeActionMap")
	public ResponseEntity<List<TraceXeActionMapDTOS>> getAllTraceXeActionMapApi(	
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
			
		List<TraceXeActionMapDTOS> traceXeActionMap = new ArrayList<TraceXeActionMapDTOS>();
		traceXeActionMapService.traceXeActionMapFindAll(page, size).forEach(e -> {
			traceXeActionMap.add(traceXeActionMapMapper.mapper(e));
		});
		return ResponseEntity.ok(traceXeActionMap);
	}

	@GetMapping("/TraceXeActionMap/{id}")
	public ResponseEntity<TraceXeActionMap> getTraceXeActionMapByIdApi(@PathVariable int id) {
		TraceXeActionMap traceXeActionMap =(TraceXeActionMap)traceXeActionMapService.traceXeActionMapFindById(id).orElse(new TraceXeActionMap());
		return ResponseEntity.ok(traceXeActionMap);
	}
	@PostMapping("/TraceXeActionMap")
	public ResponseEntity<TraceXeActionMap> addNewTraceXeActionMap(@RequestBody TraceXeActionMap entity) {
	    TraceXeActionMap saved = traceXeActionMapService.traceXeActionMapSave(entity);
	    return ResponseEntity.ok(saved);
	}

	@PutMapping("/TraceXeActionMap")
	public ResponseEntity<TraceXeActionMap> updateTraceXeActionMap(@RequestBody TraceXeActionMap entity) {
	    TraceXeActionMap updated = traceXeActionMapService.traceXeActionMapSave(entity); 
	    return ResponseEntity.ok(updated);
	}
	@DeleteMapping("/TraceXeActionMap/{id}")
	public ResponseEntity<Void> deleteTraceXeActionMap(@PathVariable int id) {
	    traceXeActionMapService.traceXeActionMapDeleteById(id); 
	    return ResponseEntity.noContent().build(); 
	}


	
}
