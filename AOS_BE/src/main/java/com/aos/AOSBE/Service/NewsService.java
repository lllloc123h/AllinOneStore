package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.News;
import com.aos.AOSBE.Repository.NewsRepository;

@Service
public class NewsService {
	@Autowired
	private GenericSpecificationBuilder specBuilder;
	@Autowired
	private NewsRepository newsRepository;

	public List<News> newsFindAll(int page, int size, Map<String, Object> filters) {
		Pageable pageable = PageRequest.of(page, size);
		Specification<News> spec = specBuilder.buildFilter(filters);
		return newsRepository.findAll(spec, pageable).getContent();
	}

	@Transactional
	public News newsSave(News news) {
		return newsRepository.save(news);
	}

	public Optional<News> newsFindById(int id) {
		return newsRepository.findById(id);
	}

	@Transactional
	public void newsDeleteById(int id) {
		newsRepository.deleteById(id);
	}
}