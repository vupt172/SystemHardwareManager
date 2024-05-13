package com.vupt.SHM.entity;

import com.vupt.SHM.constant.EquipmentStatus;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;


@Data
@Entity
@Where(clause = "is_deleted = false")
public class Equipment extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String code;
    @Column(nullable = false)
    private Date receivedDate;
    @Column(nullable = false)
    private EquipmentStatus status;
    @ManyToOne
    @JoinColumn(name="category_id",nullable = false)
    private Category category;
    @ManyToOne
    @JoinColumn(name="manager_id", nullable=false)
    private Employee manager;
    @ManyToOne
    @JoinColumn(name="department_id",nullable = false)
    private Department department;
    private String note;
    @ManyToOne
    @JoinColumn(name="equipment_group_id")
    private EquipmentGroup equipmentGroup;

    public Equipment(){}

    public String generateCode(){
        return this.category.getCode()+this.getId();
    }

}
