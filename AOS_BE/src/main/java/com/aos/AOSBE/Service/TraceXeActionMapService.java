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
public class TraceXeActionMapService {
	@Autowired
    private TraceXeActionMapRepository traceXeActionMapRepository;

    public List<TraceXeActionMap> traceXeActionMapFindAll(int page, int size) {
    	Pageable pageable = PageRequest.of(page, size);
		return traceXeActionMapRepository.findAll(pageable).getContent();
    }
    public TraceXeActionMap traceXeActionMapSave(TraceXeActionMap traceXeActionMap) {
        return traceXeActionMapRepository.save(traceXeActionMap);
    }
    public Optional<TraceXeActionMap> traceXeActionMapFindById(String id) {
        return traceXeActionMapRepository.findById(id);
    }
    public void traceXeActionMapDeleteById(String id) {
        traceXeActionMapRepository.deleteById(id);
    }
}