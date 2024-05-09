package com.vupt.SHM.services;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.CategoryRepository;
import com.vupt.SHM.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepo;
    @Autowired
    private ModelMapper modelMapper;


    public List<DepartmentDTO> findAll() {

        return departmentRepo.findAll().stream()
                .map(department -> modelMapper.map(department, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public void save(DepartmentDTO departmentDTO) {
        if(departmentDTO.getId()==0){
            Department newDepartment= modelMapper.map(departmentDTO,Department.class);
            departmentRepo.save(newDepartment);
        }
        //Department curDepartment= departmentRepo.findById(departmentDTO.getId()).get();

    }
    public Department findById(long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy department với id là "+id));
    }
    public DepartmentDTO getDTO(long id){
        Department d= this.findById(id);
        return modelMapper.map(d,DepartmentDTO.class);
    }
}
