package com.vupt.SHM.specifications;

import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.entity.Equipment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

public class EquipmentGroupSpecification {
    public static Specification<Equipment> filterSearch(Long departmentId) {
        return ((root, criteriaQuery, criteriaBuilder) -> {

            root.join("department");

            Predicate predicate = criteriaBuilder.conjunction();

            if (departmentId != 0) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("department").get("id"), departmentId));
            }
            return predicate;
        });
    }
}
