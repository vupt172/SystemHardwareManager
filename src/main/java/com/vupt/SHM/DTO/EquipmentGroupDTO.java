package com.vupt.SHM.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EquipmentGroupDTO {
    private long id;
    private String name;
    private long departmentId;
    private String departmentName;
    private List<EquipmentDTO> equipmentDTOList =new ArrayList<>();
}
