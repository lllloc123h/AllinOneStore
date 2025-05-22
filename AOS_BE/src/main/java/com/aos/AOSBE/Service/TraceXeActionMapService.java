package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
public class TraceXeActionMapService {
	@Autowired
    private TraceXeActionMapRepository traceTraceXeActionMapRepository;

    public List<TraceXeActionMap> traceTraceXeActionMapFindAll() {
        return traceTraceXeActionMapRepository.findAll();
    }
    public TraceXeActionMap traceTraceXeActionMapSave(TraceXeActionMap traceTraceXeActionMap) {
        return traceTraceXeActionMapRepository.save(traceTraceXeActionMap);
    }
    public Optional<TraceXeActionMap> traceTraceXeActionMapFindById(String id) {
        return traceTraceXeActionMapRepository.findById(id);
    }
    public void traceTraceXeActionMapDeleteById(String id) {
        traceTraceXeActionMapRepository.deleteById(id);
    }
}