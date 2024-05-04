package com.vupt.SHM.constant;

import lombok.Data;


public enum Authority {
    ROLE_ADMIN("Role Admin"),
    ROLE_USER("Role User"),
    MANAGE_CATEGORY("Manage Category"),
    MANAGE_ACCOUNT("Manage Account");
   private String title;
   Authority(String title){
       this.title=title;
   }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
