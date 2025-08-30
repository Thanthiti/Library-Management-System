package com.example.thanthiti.Library.Management.System.Mapper;

import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.CategoryUserResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.CategoryDTO.PostCategoryAdminResDTO;
import com.example.thanthiti.Library.Management.System.Entity.Category;

import java.util.List;

public class CategoryMapper
{
    public static CategoryAdminResponseDTO toAdminDTO(Category category) {
        CategoryAdminResponseDTO dto = new CategoryAdminResponseDTO();
        dto.setName(category.getName());
        dto.setBookCount(category.getBooks().size());

        List<BookResponseDTO> books = category.getBooks().stream()
                .map(book -> new BookResponseDTO(book.getTitle(), book.getAuthor(), book.getDescription()))
                .toList();

        dto.setBooks(books);
        return dto;
    }

    public static CategoryUserResponseDTO toUserDTO(Category category) {
        CategoryUserResponseDTO dto = new CategoryUserResponseDTO();
        dto.setName(category.getName());
        dto.setBookCount(category.getBooks().size());
        return dto;
    }

    public static PostCategoryAdminResDTO toResDTO(Category category, String message) {
        return new PostCategoryAdminResDTO(category.getId(), category.getName(), message);
    }
}
