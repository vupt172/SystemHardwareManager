package com.vupt.SHM.DTO;

import com.vupt.SHM.constant.EquipmentStatus;
import com.vupt.SHM.utils.DateTimeUtils;
import lombok.Data;

import java.sql.Date;

@Data
public class EquipmentDTO {
    private long id;
    private String name;
    private String code;
    private Date receivedDate;
    private EquipmentStatus status;

    private long managerId;
    private String managerName;
    private long departmentId;
    private String departmentName;
    private long categoryId;
    private String categoryName;

 /*   @Override
    public String toString() {
        return code+" - "+name+" - "+ DateTimeUtils.format(receivedDate)+" - "+status.getTitle();
    }*/
}
