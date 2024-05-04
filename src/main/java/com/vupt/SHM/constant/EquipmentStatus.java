package com.vupt.SHM.constant;

public enum EquipmentStatus {
    NEW("Mới"),
    USED("Đang sử dụng"),
    DESTROYED("Bị hư"),
    STORAGE("Tồn kho");
    private String title;
    EquipmentStatus(String title){
        this.title=title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
