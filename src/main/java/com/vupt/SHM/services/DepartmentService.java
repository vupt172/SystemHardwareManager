package com.vupt.SHM.services;

import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.DTO.DepartmentDTO;
import com.vupt.SHM.constant.AppConstants;
import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.entity.Department;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.CategoryRepository;
import com.vupt.SHM.repositories.DepartmentRepository;
import com.vupt.SHM.specifications.DepartmentSpecification;
import com.vupt.SHM.utils.DisplayMessage;
import com.vupt.SHM.views.popup.DepartmentEdit;
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
            checkIfExistsByName(departmentDTO.getName());
            Department newDepartment= modelMapper.map(departmentDTO,Department.class);
            departmentRepo.save(newDepartment);
        }
        else{
            Department curDepartment = departmentRepo.findById(departmentDTO.getId()).get();
            if(!curDepartment.getName().equals(departmentDTO.getName()))
                checkIfExistsByName(departmentDTO.getName());
            modelMapper.map(departmentDTO,curDepartment);
            departmentRepo.save(curDepartment);
        }
        //Department curDepartment= departmentRepo.findById(departmentDTO.getId()).get();

    }
    public Department findById(long id) {
        return departmentRepo.findById(id)
                .orElseThrow(() -> new AppException(DisplayMessage.getNotFoundMessage(AppConstants.MENU_DEPARTMENT,"id",id)));
    }
    public DepartmentDTO getDTO(long id){
        Department d= this.findById(id);
        return modelMapper.map(d,DepartmentDTO.class);
    }
    public void checkIfExistsByName(String name){
        if (departmentRepo.existsByName(name))
            throw new AppException("Bộ phận đã tồn tại với tên là " + name);
    }

    public void softDelete(long id) {
        Department department=findById(id);
        department.setDeleted(true);
        departmentRepo.save(department);
    }

    public List<DepartmentDTO> search(String name, DepartmentType type) {
       return departmentRepo.findAll(DepartmentSpecification.containsNameAndTypeIfPresent(name,type)).stream()
               .map(department -> modelMapper.map(department, DepartmentDTO.class))
               .collect(Collectors.toList());
    }
}
