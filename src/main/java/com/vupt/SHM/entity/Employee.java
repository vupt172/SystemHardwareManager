package com.vupt.SHM.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Employee extends BaseEntity {
    private String username;
    private String fullName;
    @ManyToOne
    @JoinColumn(name="department_id",nullable = false)
    private Department department;
    private String contact;
  public Employee(){}
    public Employee(String fullName){
      this.fullName=fullName;
    }

}
