package com.example.thanthiti.Library.Management.System.DTO.CategoryDTO;

public class CategoryUserResponseDTO {
    private String name;
    private int bookCount;

    public CategoryUserResponseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBookCount() {
        return bookCount;
    }

    public void setBookCount(int bookCount) {
        this.bookCount = bookCount;
    }
}
