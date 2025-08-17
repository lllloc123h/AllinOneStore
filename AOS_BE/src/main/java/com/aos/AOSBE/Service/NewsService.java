package com.aos.AOSBE.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aos.AOSBE.Entity.EWalletTransactions;
import com.aos.AOSBE.Entity.News;
import com.aos.AOSBE.Repository.NewsRepository;
import com.aos.AOSBE.DTOS.FilterNews; // Import FilterNews DTO
import com.aos.AOSBE.CommonFunctions.NewsSpecification; // Import NewsSpecification class của bạn

@Service
public class NewsService {

	@Autowired
	private GenericSpecificationBuilder specBuilder;
    @Autowired
    private NewsRepository newsRepository; 
    /**
     * Tìm kiếm tin tức với phân trang và lọc động sử dụng FilterNews DTO.
     *
     * @param page Số trang (bắt đầu từ 0).
     * @param size Số lượng mục trên mỗi trang.
     * @param filterNews DTO chứa các tiêu chí lọc (keyword, category, startDate, endDate).
     * @return Một Page các đối tượng News đã được lọc và phân trang.
     */
    public Page<News> newsFindAll(int page, int size, Map<String, Object> filters) {
        Pageable pageable = PageRequest.of(page, size);

        // Sử dụng NewsSpecification để xây dựng Specification từ FilterNews DTO
        // NewsSpecification.byCriteria() là một phương thức static, không cần @Autowired

		Specification<News> spec = specBuilder.buildFilter(filters);
//        Specification<News> spec = NewsSpecification.byCriteria(filterNews);

        return newsRepository.findAll(spec, pageable);
    }

    public List<News> findLastedNews() {
        return newsRepository.findTopByHomeTrueOrderByCreatedAtDesc();
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