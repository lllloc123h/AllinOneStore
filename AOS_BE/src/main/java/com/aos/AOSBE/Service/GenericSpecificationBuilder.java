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
					if (value instanceof String) {
						String stringValue = (String) value;
						if (!stringValue.trim().isEmpty()) {
							predicates.add(cb.like(cb.lower(root.get(field)), "%" + stringValue.toLowerCase() + "%"));
						}
					} else {
						try {
							predicates.add(cb.equal(root.get(field), value));
						} catch (IllegalArgumentException e) {
							// Optional: log field name causing the error
							System.err.println("Skipping invalid filter field: " + field);
						}
					}
				}
			});
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}
