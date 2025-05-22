package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class TraceXeEventMapService {
	@Autowired
    private TraceXeEventMapRepository traceTraceXeEventMapRepository;

    public List<TraceXeEventMap> traceTraceXeEventMapFindAll() {
        return traceTraceXeEventMapRepository.findAll();
    }
    public TraceXeEventMap traceTraceXeEventMapSave(TraceXeEventMap traceTraceXeEventMap) {
        return traceTraceXeEventMapRepository.save(traceTraceXeEventMap);
    }
    public Optional<TraceXeEventMap> traceTraceXeEventMapFindById(String id) {
        return traceTraceXeEventMapRepository.findById(id);
    }
    public void traceTraceXeEventMapDeleteById(String id) {
        traceTraceXeEventMapRepository.deleteById(id);
    }
}