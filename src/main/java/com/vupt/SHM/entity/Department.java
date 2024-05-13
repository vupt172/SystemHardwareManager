package com.vupt.SHM.entity;

import com.vupt.SHM.constant.DepartmentType;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Where(clause = "is_deleted = false")
public class Department extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private DepartmentType type;
    private boolean isSuspended=false;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employeeList=new ArrayList<>();
    public Department(){}

    public Department(String name, DepartmentType departmentType) {
        this.name = name;
        this.type = departmentType;
    }
}
