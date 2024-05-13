package com.vupt.SHM.specifications;

import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.entity.Department;
import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpecification {
    public static Specification<Department> containsNameAndTypeIfPresent(String name, DepartmentType type) {
        return (root, query, criteriaBuilder) -> {
            if (name == null && type == null) {
                return criteriaBuilder.conjunction(); // Không áp dụng điều kiện nếu cả hai tham số là null
            }
            else if (name != null && type != null) {
                return criteriaBuilder.and(
                        criteriaBuilder.like(root.get("name"), "%" + name + "%"),
                        criteriaBuilder.equal(root.get("type"), type)
                );
            }
            else if (name != null) {
                return criteriaBuilder.like(root.get("name"), "%" + name + "%");
            }
            else {
                return criteriaBuilder.equal(root.get("type"), type);
            }
        };
    }
}
