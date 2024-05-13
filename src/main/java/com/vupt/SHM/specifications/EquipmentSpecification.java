package com.vupt.SHM.specifications;

import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.entity.Equipment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;


public class EquipmentSpecification {
    public static Specification<Equipment> filterSearch(String name, String code, EquipmentStatus status, Long categoryId, Long managerId, Long departmentId) {
        return ((root, criteriaQuery, criteriaBuilder) -> {
            root.join("category");
            root.join("manager");
            root.join("department");

            Predicate predicate = criteriaBuilder.conjunction();
            if (name != null && !name.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (code != null && !code.isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("code"), "%" + code + "%"));
            }
            if (status != null) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("status"), status));
            }
            if (categoryId != 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("category").get("id"), categoryId));
            }
            if (managerId != 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("manager").get("id"), managerId));
            }
            if (departmentId != 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("department").get("id"), departmentId));
            }
            return predicate;
        });
    }
}
