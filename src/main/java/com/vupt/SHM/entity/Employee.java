package com.vupt.SHM.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Entity
@Where(clause = "is_deleted = false")
public class Employee extends BaseEntity {
    private String username;
    @Column(nullable = false)
    private String fullName;
    @ManyToOne
    @JoinColumn(name="department_id",nullable = false)
    private Department department;
    private String contact;
    private boolean isManager=false;
  public Employee(){}
    public Employee(String fullName){
      this.fullName=fullName;
    }

}
