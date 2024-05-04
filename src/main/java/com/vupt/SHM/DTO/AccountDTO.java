package com.vupt.SHM.DTO;

import lombok.Data;

@Data
public class AccountDTO {
    private  long id;
    private String username;
    private String fullName;
    private String password;
    private boolean isSuspended;
}
