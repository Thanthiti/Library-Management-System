package com.example.thanthiti.Library.Management.System.DTO.BookDTO;

public class BookAdminResponseDTO {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String categoryName;

    public BookAdminResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
