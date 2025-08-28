package com.example.thanthiti.Library.Management.System.DTO.CategoryDTO;

public class PostCategoryAdminResDTO {

    private Long id;
    private String name;
    private String message;

    public PostCategoryAdminResDTO() {
    }

    public PostCategoryAdminResDTO(String name, String message) {
        this.message = message;
        this.name = name;
    }

    public PostCategoryAdminResDTO(Long id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
