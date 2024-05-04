package com.vupt.SHM.entity;

import com.vupt.SHM.constant.Authority;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Role extends BaseEntity {
  private Authority authority;
  private String note;

  public Role() {
  }

  public Role(Authority authority) {
    this.authority = authority;
  }
}
