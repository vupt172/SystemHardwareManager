package com.vupt.SHM.services;

import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.entity.Employee;
import com.vupt.SHM.entity.Equipment;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.CategoryRepository;
import com.vupt.SHM.repositories.DepartmentRepository;
import com.vupt.SHM.repositories.EmployeeRepository;
import com.vupt.SHM.repositories.EquipmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentService {
    @Autowired
    EquipmentRepository equipmentRepo;
    @Autowired
    CategoryService categoryService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ModelMapper modelMapper;

    public List<EquipmentDTO> findAll(){
        return equipmentRepo.findAll().stream()
                .map(equipment -> modelMapper.map(equipment,EquipmentDTO.class))
                .collect(Collectors.toList());
    }
    public void save(EquipmentDTO equipmentDTO){
        if(equipmentDTO.getId()==0){
            Equipment newEquipment=new Equipment();
            modelMapper.map(equipmentDTO,newEquipment);

            Category category = categoryService.findById(equipmentDTO.getCategoryId());
            newEquipment.setCategory(category);
            Department department=departmentService.findById(equipmentDTO.getDepartmentId());
            newEquipment.setDepartment(department);
            Employee manager=employeeService.findById(equipmentDTO.getManagerId());
            newEquipment.setManager(manager);

            equipmentRepo.save(newEquipment);
            newEquipment.setCode(newEquipment.generateCode());
            equipmentRepo.save(newEquipment);
        }
    }
}
