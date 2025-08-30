package com.example.thanthiti.Library.Management.System.Mapper;

import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookAdminRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookResponseDTO;
import com.example.thanthiti.Library.Management.System.Entity.Book;

public class BookMapper {

    // Entity -> User Response DTO
    public static BookResponseDTO toBookResponseDTO(Book book) {
        BookResponseDTO dto = new BookResponseDTO();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        return dto;
    }

    // Entity -> Admin Response DTO
    public static BookAdminResponseDTO toBookAdminResponseDTO(Book book) {
        BookAdminResponseDTO dto = new BookAdminResponseDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setDescription(book.getDescription());
        if (book.getCategory() != null) {
            dto.setCategoryName(book.getCategory().getName());
        }
        return dto;
    }

    // Request DTO -> Entity (ใช้ตอน add/update)
    public static Book toBook(BookAdminRequestDTO request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setDescription(request.getDescription());
        return book;
    }
}
