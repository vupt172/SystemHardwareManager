package com.vupt.SHM.repositories;

import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.entity.EquipmentGroup;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentGroupRepository extends JpaRepository<EquipmentGroup,Long> {
    List<Department> findAll(Specification<EquipmentGroup> spec);
}
