package com.vupt.SHM.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Where(clause = "is_deleted = false")
public class Account extends BaseEntity {

    private String username;
    private String password;
    private String fullName;
    private boolean isSuspended=false;

    @OneToMany(cascade = CascadeType.PERSIST,
    fetch = FetchType.EAGER
    )
    private List<Role> roles=new ArrayList<>();
    public Account(){
    }

    public Account(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }
}
