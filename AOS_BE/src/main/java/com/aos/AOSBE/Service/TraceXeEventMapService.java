package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class TraceXeEventMapService {
	@Autowired
    private TraceXeEventMapRepository traceXeEventMapRepository;

    public List<TraceXeEventMap> traceXeEventMapFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return traceXeEventMapRepository.findAll(pageable).getContent();
    }
    public TraceXeEventMap traceXeEventMapSave(TraceXeEventMap traceXeEventMap) {
        return traceXeEventMapRepository.save(traceXeEventMap);
    }
    public Optional<TraceXeEventMap> traceXeEventMapFindById(String id) {
        return traceXeEventMapRepository.findById(id);
    }
    public void traceXeEventMapDeleteById(String id) {
        traceXeEventMapRepository.deleteById(id);
    }
}