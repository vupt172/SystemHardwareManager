package com.vupt.SHM.DTO;

import lombok.Data;

@Data
public class EquipmentGroupDTO {
    private long id;
    private String name;
    private long departmentId;
    private String departmentName;
}
