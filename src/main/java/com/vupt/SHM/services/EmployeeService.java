package com.vupt.SHM.services;

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

    public void save(EmployeeDTO employeeDTO) {
        if (employeeDTO.getId() == 0) {
            Employee newEmployee = modelMapper.map(employeeDTO, Employee.class);
            Department department = departmentService.findById(employeeDTO.getId());
            newEmployee.setDepartment(department);
            employeeRepo.save(newEmployee);
        }
    }

    public Employee findById(long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy employee với id là " + id));
    }

}

