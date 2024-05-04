package com.vupt.SHM.services;

import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.DTO.EquipmentGroupDTO;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.entity.Employee;
import com.vupt.SHM.entity.EquipmentGroup;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.DepartmentRepository;
import com.vupt.SHM.repositories.EmployeeRepository;
import com.vupt.SHM.repositories.EquipmentGroupRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentGroupService {
    @Autowired
    EquipmentGroupRepository equipmentGroupRepo;
    @Autowired
    DepartmentService departmentService;

    @Autowired
    ModelMapper modelMapper;

    public List<EquipmentGroupDTO> findALl() {
        return equipmentGroupRepo.findAll().stream()
                .map(equipmentGroup -> modelMapper.map(equipmentGroup, EquipmentGroupDTO.class))
                .collect(Collectors.toList());
    }

    public void save(EquipmentGroupDTO equipmentGroupDTO) {
        if (equipmentGroupDTO.getId() == 0) {
            EquipmentGroup equipmentGroup = modelMapper.map(equipmentGroupDTO, EquipmentGroup.class);

            Department department = departmentService.findById(equipmentGroupDTO.getDepartmentId());
            equipmentGroup.setDepartment(department);

            equipmentGroupRepo.save(equipmentGroup);
        }
    }
}

