//package com.aos.AOSBE.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.aos.AOSBE.Entity.Sysdiagrams;
//import com.aos.AOSBE.Repository.SysdiagramsRepository;
//
//@Service
//public class SysdiagramsService {
//	private GenericSpecificationBuilder specBuilder;
//private GenericSpecificationBuilder specBuilder;
//@Autowired
//	private SysdiagramsRepository sysdiagramsRepository;
//
//	public List<Sysdiagrams> sysdiagramsFindAll(int page, int size, Map<String, Object> filters) {
//		Pageable pageable = PageRequest.of(page, size);
//		Specification<Sysdiagrams> spec = specBuilder.buildFilter(filters);
//		return sysdiagramsRepository.findAll(spec, pageable).getContent();
//	}
//
//	@Transactional
//	public Sysdiagrams sysdiagramsSave(Sysdiagrams sysdiagrams) {
//		return sysdiagramsRepository.save(sysdiagrams);
//	}
//
//	public Optional<Sysdiagrams> sysdiagramsFindById(String id) {
//		return sysdiagramsRepository.findById(id);
//	}
//
//	@Transactional
//	public void sysdiagramsDeleteById(String id) {
//		sysdiagramsRepository.deleteById(id);
//	}
//}