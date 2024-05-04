package com.vupt.SHM.modelmapper.configuration;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.EmployeeDTO;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.entity.Employee;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;

public class EmployeeMap extends PropertyMap<Employee, EmployeeDTO> {

    @Override
    protected void configure() {
      map().setDepartmentId(source.getDepartment().getId());
      map().setDepartmentName(source.getDepartment().getName());
    }


}
