package com.vupt.SHM.entity;

import lombok.Data;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Data
@Entity
public class EquipmentGroup extends BaseEntity {
    private String name;
    @OneToMany(fetch = FetchType.EAGER,mappedBy = "equipmentGroup",cascade = CascadeType.PERSIST)
    private List<Equipment> equipmentList =new ArrayList<>();

    @ManyToOne
    @JoinColumn(name="department_id",nullable = false)
    private Department department;
}
