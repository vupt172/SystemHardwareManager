package com.vupt.SHM.DTO;

import com.vupt.SHM.constant.EquipmentStatus;
import lombok.Data;

import javax.persistence.Entity;

@Data
public class EmployeeDTO{
    private long id;
    private String fullName;
    private String username;
    private long departmentId;
    private String departmentName;
    private String contact;
    private boolean isManager;
    @Override
    public String toString() {
        return this.fullName;
    }
}
