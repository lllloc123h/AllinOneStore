package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import com.aos.AOSBE.Entity.*;
import com.aos.AOSBE.Repository.*;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class TraceXeActionMapService {
	@Autowired
    private TraceXeActionMapRepository traceXeActionMapRepository;

    public List<TraceXeActionMap> traceXeActionMapFindAll() {
        return traceXeActionMapRepository.findAll();
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