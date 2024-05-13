package com.vupt.SHM.services;

import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.entity.Employee;
import com.vupt.SHM.entity.Equipment;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.CategoryRepository;
import com.vupt.SHM.repositories.DepartmentRepository;
import com.vupt.SHM.repositories.EmployeeRepository;
import com.vupt.SHM.repositories.EquipmentRepository;
import com.vupt.SHM.specifications.EquipmentSpecification;
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
    public List<EquipmentDTO> selectEquipment(long departmentId,long categoryId){
        return equipmentRepo.findAll().stream()
                .filter(e-> e.getDepartment().getId()==departmentId)
                .filter(e->e.getCategory().getId() == categoryId)
                .filter(e->e.getEquipmentGroup() ==null)
                .filter(e->e.getStatus() != EquipmentStatus.DESTROYED)
                .map(e-> modelMapper.map(e,EquipmentDTO.class))
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
        else {
            Equipment curEquipment= findById(equipmentDTO.getId());

            modelMapper.map(equipmentDTO,curEquipment);
            Category category= categoryService.findById(equipmentDTO.getCategoryId());
            Employee employee=employeeService.findById(equipmentDTO.getManagerId());
            Department department=departmentService.findById(equipmentDTO.getDepartmentId());
            curEquipment.setCategory(category);
            curEquipment.setManager(employee);
            curEquipment.setDepartment(department);

            equipmentRepo.save(curEquipment);
        }
    }

    public Equipment findById(long id) {
        return equipmentRepo.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy equipment với id là "+id));
    }

    public void softDelete(long id) {
        Equipment equipment=findById(id);
        equipment.setDeleted(true);
        equipmentRepo.save(equipment);
    }
    public List<EquipmentDTO> search(String name,String code,EquipmentStatus status,long categoryId,long managerId,long departmentId){
      return   equipmentRepo.findAll(EquipmentSpecification.filterSearch(name,code,status,categoryId,managerId,departmentId)).stream()
                .map(equipment -> modelMapper.map(equipment,EquipmentDTO.class))
                .collect(Collectors.toList());
    }
}
