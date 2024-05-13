package com.vupt.SHM.repositories;

import com.vupt.SHM.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    boolean existsByFullName(String name);
    boolean existsByUsername(String name);
    List<Employee> findByIsManager(Boolean isManager);
}
