package com.vupt.SHM.services;

import com.vupt.SHM.DTO.RepairterDTO;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.entity.Employee;
import com.vupt.SHM.entity.Repairter;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.RepairterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepairterService {
    @Autowired
    RepairterRepository repairterRepo;
    @Autowired
    ModelMapper modelMapper;

    public List<RepairterDTO> findALl() {
        return repairterRepo.findAll().stream()
                .map(employee -> modelMapper.map(employee, RepairterDTO.class))
                .collect(Collectors.toList());
    }

    public void save(RepairterDTO repairterDTO) {
        if (repairterDTO.getId() == 0) {
            Repairter newRepairter = modelMapper.map(repairterDTO, Repairter.class);
            repairterRepo.save(newRepairter);
        }
    }
}
