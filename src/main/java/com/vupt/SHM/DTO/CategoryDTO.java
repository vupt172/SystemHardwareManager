package com.vupt.SHM.DTO;

import lombok.Data;

@Data
public class CategoryDTO {
    private long id;
    private String name;
    private String code;
    private boolean isSuspended;

    @Override
    public String toString() {
        return this.name;
    }
}
