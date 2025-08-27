package com.example.thanthiti.Library.Management.System.DTO.CategoryDTO;

import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookResponseDTO;

import java.util.List;

public class CategoryAdminResponseDTO {
    private String name;
    private Integer bookCount;
    private List<BookResponseDTO> books;

    public CategoryAdminResponseDTO() {
    }
    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public List<BookResponseDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponseDTO> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


