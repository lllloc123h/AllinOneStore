package com.aos.AOSBE.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Predicate;

@Component
public class GenericSpecificationBuilder {
	public <T> Specification<T> buildFilter(Map<String, Object> filters) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			filters.forEach((field, value) -> {
				if (value != null) {
					if (value instanceof String && !((String) value).isEmpty()) {
						predicates.add(cb.like(cb.lower(root.get(field)), "%" + value.toString().toLowerCase() + "%"));
					} else {
						predicates.add(cb.equal(root.get(field), value));
					}
				}
			});
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
