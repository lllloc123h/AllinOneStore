package com.aos.AOSBE.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

@Component
public class GenericSpecificationBuilder {
	public <T> Specification<T> buildFilter(Map<String, Object> filters) {
		return (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();

			filters.forEach((key, value) -> {
				if (value != null) {
					try {
						String field = key;
						String operator = "eq"; // default
						if (key.endsWith("From")) {
							field = key.substring(0, key.length() - 4); // remove "From"
							operator = "gte";
						} else if (key.endsWith("To")) {
							field = key.substring(0, key.length() - 2); // remove "To"
							operator = "lte";
						}
						Class<?> fieldType = root.get(field).getJavaType();
						Path<?> path = root.get(field);

						if (fieldType == String.class) {
							String stringValue = value.toString().trim();
							if (!stringValue.isEmpty()) {
								predicates.add(cb.like(cb.lower(path.as(String.class)),
										"%" + stringValue.toLowerCase() + "%"));
							}
						} else if (Number.class.isAssignableFrom(fieldType)) {
							Number numberValue = convertToNumber(value.toString(), fieldType);
							Expression<? extends Number> numberExpr = root.get(field);
							switch (operator) {
							case "gte":
								predicates.add(cb.ge(numberExpr, numberValue));
								break;
							case "lte":
								predicates.add(cb.le(numberExpr, numberValue));
								break;
							default:
								predicates.add(cb.equal(path, numberValue));
								break;
							}
						} else if (fieldType == java.time.LocalDateTime.class) {
							LocalDateTime dateTimeValue = LocalDateTime.parse(value.toString());
							Path<LocalDateTime> datePath = root.get(field);
							switch (operator) {
							case "gte":
								predicates.add(cb.greaterThanOrEqualTo(datePath, dateTimeValue));
								break;
							case "lte":
								predicates.add(cb.lessThanOrEqualTo(datePath, dateTimeValue));
								break;
							default:
								predicates.add(cb.equal(datePath, dateTimeValue));
								break;
							}
						} else {
							predicates.add(cb.equal(path, value));
						}
					} catch (Exception e) {
						System.err.println("Skipping invalid filter field: " + key + " - " + e.getMessage());
					}
				}
			});
			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}

	private Number convertToNumber(String value, Class<?> targetType) {
		if (targetType == Integer.class)
			return Integer.parseInt(value);
		if (targetType == Long.class)
			return Long.parseLong(value);
		if (targetType == Double.class)
			return Double.parseDouble(value);
		if (targetType == Float.class)
			return Float.parseFloat(value);
		// Add more if needed
		throw new IllegalArgumentException("Unsupported number type: " + targetType);
	}
}
