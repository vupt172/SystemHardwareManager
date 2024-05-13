package com.vupt.SHM.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;

@Data
@Entity
@Where(clause = "is_deleted = false")
public class Repairter extends BaseEntity{
    private String name;
    private String company;
    private String phone;
    private String note;
    public Repairter(){}

    public Repairter(String name,String company,String phone){
        this.name=name;
        this.company=company;
        this.phone=phone;
    }
}
