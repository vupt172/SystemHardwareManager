package com.vupt.SHM.repositories;

import com.vupt.SHM.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long>
{
}
