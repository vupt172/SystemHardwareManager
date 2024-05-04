package com.vupt.SHM.DTO;

import com.vupt.SHM.constant.DepartmentType;
import lombok.Data;

@Data
public class DepartmentDTO {
    private long id;
    private String name;
    private DepartmentType departmentType;
    private boolean isSuspended;
    public DepartmentDTO(){}

    public DepartmentDTO(long id, String name, DepartmentType departmentType, boolean isSuspended) {
        this.id = id;
        this.name = name;
        this.departmentType = departmentType;
        this.isSuspended = isSuspended;
    }
    @Override
    public String toString() {
        return this.name;
    }
}
