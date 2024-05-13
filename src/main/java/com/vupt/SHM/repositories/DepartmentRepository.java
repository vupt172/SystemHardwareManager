package com.vupt.SHM.repositories;

import com.vupt.SHM.constant.DepartmentType;
import com.vupt.SHM.entity.Department;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department,Long>
{
    boolean existsByName(String name);
    List<Department> findAll(Specification<Department> spec);
  /*  @Query("SELECT d from Department d WHERE d.name=:name AND d.type=:departmentType")
    List<Department> findByNameAndDepartmentType(String name, DepartmentType departmentType);*/
}
