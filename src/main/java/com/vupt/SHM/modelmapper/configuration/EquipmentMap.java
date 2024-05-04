package com.vupt.SHM.modelmapper.configuration;

import com.vupt.SHM.DTO.EquipmentDTO;
import com.vupt.SHM.entity.Equipment;
import org.modelmapper.PropertyMap;


public class EquipmentMap extends PropertyMap<Equipment, EquipmentDTO> {

    @Override
    protected void configure() {
        map().setCategoryId(source.getCategory().getId());
        map().setCategoryName(source.getCategory().getName());
        map().setDepartmentId(source.getDepartment().getId());
        map().setDepartmentName(source.getDepartment().getName());
        map().setManagerId(source.getManager().getId());
        map().setManagerName(source.getManager().getFullName());
    }
}
