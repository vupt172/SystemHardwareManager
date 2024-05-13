package com.vupt.SHM.repositories;

import com.vupt.SHM.entity.Equipment;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment,Long> {
 List<Equipment> findAll(Specification<Equipment> spec);
}
