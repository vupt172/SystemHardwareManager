package com.vupt.SHM.services;

import com.vupt.SHM.DTO.CategoryDTO;
import com.vupt.SHM.entity.Category;
import com.vupt.SHM.exceptions.AppException;
import com.vupt.SHM.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public void save(CategoryDTO categoryDTO) {
        if (categoryDTO.getId() == 0) {
            /*if(categoryRepo.existsByName(category.getName()))
                throw new AppException("Danh mục đã tồn tại với tên là "+ category.getName());*/
            if (categoryRepo.existsByCode(categoryDTO.getCode()))
                throw new AppException("Danh mục đã tồn tại với code là " + categoryDTO.getCode());

            Category category = modelMapper.map(categoryDTO, Category.class);
            categoryRepo.save(category);
        } else {
            Category dataCategory = categoryRepo.findById(categoryDTO.getId()).get();
            if (!dataCategory.getCode().equals(categoryDTO.getCode())) {
                if (categoryRepo.existsByCode(categoryDTO.getCode()))
                    throw new AppException("Danh mục đã tồn tại với code là " + categoryDTO.getCode());
            }

            modelMapper.map(categoryDTO, dataCategory);
            categoryRepo.save(dataCategory);
        }
    }

    public void softDelete(long id) {
        Category category = categoryRepo.findById(id).orElseThrow(() -> new AppException("Không tim thấy category với id =" + id));
        category.setDeleted(true);
        categoryRepo.save(category);
    }

    public void update(Category category) {

    }

    public Category findById(long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new AppException("Không tìm thấy category với id là "+id));
    }
}
