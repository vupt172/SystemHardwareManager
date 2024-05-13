package com.vupt.SHM.services;

import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.constant.AppConstants;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.CategoryRepository;
import com.vupt.SHM.utils.DisplayMessage;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    public List<CategoryDTO> findAll() {
        return categoryRepo.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());

    }

    public List<CategoryDTO> findAllIfNotSuspended() {
        return categoryRepo.findAll().stream()
                .filter(e -> !e.isSuspended())
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public void save(CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == 0) {
            checkIfExistsByCode(categoryDTO.getCode());
            Category category = modelMapper.map(categoryDTO, Category.class);
            categoryRepo.save(category);
        } else {
            Category curCategory = categoryRepo.findById(categoryDTO.getId()).get();
            if (!curCategory.getCode().equals(categoryDTO.getCode())) {
                checkIfExistsByCode(categoryDTO.getCode());
            }
            modelMapper.map(categoryDTO, curCategory);
            categoryRepo.save(curCategory);
        }
    }

    public void softDelete(long id) {
        Category category =findById(id);
        category.setDeleted(true);
        categoryRepo.save(category);
    }

    public void update(Category category) {

    }

    public Category findById(long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new AppException(DisplayMessage.getNotFoundMessage(AppConstants.MENU_CATEGORY,"id",id)));
    }
    public CategoryDTO getDTO(long id){
        Category c= this.findById(id);
        return modelMapper.map(c,CategoryDTO.class);
    }

    public void checkIfExistsByCode(String code) {
        if (categoryRepo.existsByCode(code))
            throw new AppException("Danh mục đã tồn tại với code là " + code);
    }
}
