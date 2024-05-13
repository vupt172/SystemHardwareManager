package com.vupt.SHM.services;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.entity.Employee;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.DepartmentRepository;
import com.vupt.SHM.repositories.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepo;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    ModelMapper modelMapper;

    public List<EmployeeDTO> findALl() {
        return employeeRepo.findAll().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .collect(Collectors.toList());
    }
    public List<EmployeeDTO> findByIsManager(){
      return  employeeRepo.findByIsManager(true).stream()
              .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
              .collect(Collectors.toList());
    }

    public void save(EmployeeDTO employeeDTO) {
        if (employeeDTO.getId() == 0) {
            checkIfExistsByName(employeeDTO.getFullName());
            checkIfExistsByUsername(employeeDTO.getUsername());
            Employee newEmployee = modelMapper.map(employeeDTO, Employee.class);
            Department department = departmentService.findById(employeeDTO.getDepartmentId());
            newEmployee.setDepartment(department);
            employeeRepo.save(newEmployee);
        }
        else{
            Employee curEmployee=  findById(employeeDTO.getId());
            if(!curEmployee.getFullName().equals(employeeDTO.getFullName())){
                checkIfExistsByName(employeeDTO.getFullName());
            }
            if(curEmployee.getUsername()!=null && !curEmployee.getUsername().equals(employeeDTO.getUsername())){
                checkIfExistsByUsername(employeeDTO.getUsername());
            }
            modelMapper.map(employeeDTO,curEmployee);
            employeeRepo.save(curEmployee);
        }
    }
    public void softDelete(long id) {
        Employee employee=findById(id);
        employee.setDeleted(true);
        employeeRepo.save(employee);
    }

    public Employee findById(long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy employee với id là " + id));
    }
    public EmployeeDTO getDTO(long id){
        Employee e= this.findById(id);
        return modelMapper.map(e,EmployeeDTO.class);
    }
    public void checkIfExistsByName(String name) {
        if (employeeRepo.existsByFullName(name))
            throw new AppException("Nhân viên đã tồn tại với tên là " + name);
    }
    public void checkIfExistsByUsername(String username){
        if(employeeRepo.existsByUsername(username))
            throw new AppException("Nhân viên đã tồn tại với username là "+username);
    }


}

