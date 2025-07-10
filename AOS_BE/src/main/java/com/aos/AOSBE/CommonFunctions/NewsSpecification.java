
package com.aos.AOSBE.CommonFunctions;


import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import com.aos.AOSBE.DTOS.FilterNews;
import com.aos.AOSBE.Entity.News;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NewsSpecification {

    public static Specification<News> byCriteria(FilterNews criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1. Lọc theo từ khóa (trong title hoặc content)
            if (criteria.getKeyword() != null && !criteria.getKeyword().isEmpty()) {
                String likeKeyword = "%" + criteria.getKeyword().toLowerCase() + "%";
                Predicate titlePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likeKeyword);
                Predicate contentPredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("content")), likeKeyword);
                predicates.add(criteriaBuilder.or(titlePredicate, contentPredicate));
            }

            // 2. Lọc theo danh mục
            if (criteria.getCategory() != null && !criteria.getCategory().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), criteria.getCategory()));
            }

            // 3. Lọc theo khoảng ngày đăng
            if (criteria.getStartDate() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("publishDate"), criteria.getStartDate()));
            }
            if (criteria.getEndDate() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("publishDate"), criteria.getEndDate()));
            }
            
            // Sắp xếp theo ngày đăng giảm dần (tùy chọn)
            query.orderBy(criteriaBuilder.desc(root.get("publishDate")));

            // Kết hợp tất cả các điều kiện bằng AND
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}