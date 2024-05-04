package com.vupt.SHM.constant;

import lombok.Data;


public enum DepartmentType {
    PHONG("Phòng"),
    TO("Tổ"),
    KHOA("Khoa");

    private String title;

     DepartmentType(String title){
        this.title=title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}
