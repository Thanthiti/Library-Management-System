package com.example.thanthiti.Library.Management.System.DTO.BookDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookAdminRequestDTO {

    @NotBlank(message =  "Title is required")
    private String title;

    @NotBlank(message =  "Author is required")
    private String author;

    @NotBlank(message =  "Description is required")
    private String description;

    @NotNull(message = "CategoryId is required")
    private Long categoryId;

    public BookAdminRequestDTO() {
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
