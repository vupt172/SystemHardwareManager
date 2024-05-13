package com.vupt.SHM.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
@Where(clause = "is_deleted = false")
public class Category extends BaseEntity{
  @Column(nullable = false)
  private String name;
  @Column(nullable = false)
  String code;
  private boolean isSuspended = false;
  public Category(){}

  public Category(String name, String code) {
    this.name = name;
    this.code = code;
  }
}
