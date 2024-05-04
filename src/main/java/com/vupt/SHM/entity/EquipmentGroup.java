package com.vupt.SHM.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
public class EquipmentGroup extends BaseEntity {
    private String name;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "equipmentGroup")
    private List<Equipment> equipmentList;

    @ManyToOne
    @JoinColumn(name="department_id",nullable = false)
    private Department department;
}
