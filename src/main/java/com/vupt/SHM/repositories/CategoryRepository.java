package com.vupt.SHM.repositories;

import com.vupt.SHM.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
   boolean existsByName(String name);
   boolean existsByCode(String code);
}
