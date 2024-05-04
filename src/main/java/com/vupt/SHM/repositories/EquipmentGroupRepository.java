package com.vupt.SHM.repositories;

import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.entity.EquipmentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentGroupRepository extends JpaRepository<EquipmentGroup,Long> {

}
