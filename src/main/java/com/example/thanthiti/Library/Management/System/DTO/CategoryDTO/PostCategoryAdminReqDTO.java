package com.example.thanthiti.Library.Management.System.DTO.CategoryDTO;

import jakarta.validation.constraints.NotBlank;

public class PostCategoryAdminReqDTO {
    @NotBlank(message = "Name is required")
    private String name;

    public PostCategoryAdminReqDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
