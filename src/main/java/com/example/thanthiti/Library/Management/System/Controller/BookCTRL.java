package com.example.thanthiti.Library.Management.System.Controller;

import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookAdminRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookResponseDTO;

import com.example.thanthiti.Library.Management.System.Repository.BookRepository;
import com.example.thanthiti.Library.Management.System.Service.BookService;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BookCTRL {

    private final BookService bookService;
    public BookCTRL(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/books")
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        List<BookResponseDTO> bookResponseDTO = bookService.getAllBooks();
        return ResponseEntity.ok(bookResponseDTO);
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/books/{id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long id) {
        BookResponseDTO bookResponseDTO = bookService.getBookById(id);
        return ResponseEntity.ok(bookResponseDTO);
    }

//    Admin can add, update, delete book

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/books")
    public ResponseEntity<BookAdminResponseDTO> addBook(@Valid @RequestBody BookAdminRequestDTO bookAdminRequestDTO) {
        // Logic to add book
        BookAdminResponseDTO bookAdminResponseDTO = bookService.addBook(bookAdminRequestDTO);
        return ResponseEntity.status(201).body(bookAdminResponseDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("admin/books/{id}")
    public ResponseEntity<BookAdminResponseDTO> updateBook(@PathVariable Long id,@RequestBody BookAdminRequestDTO bookAdminRequestDTO) {
        BookAdminResponseDTO updatedBook = bookService.updateBook(id, bookAdminRequestDTO);
        return ResponseEntity.ok(updatedBook);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("admin/books/{id}")
    public ResponseEntity<BookAdminResponseDTO> deleteBook(@PathVariable Long id) {
        BookAdminResponseDTO deletedBook = bookService.deleteBook(id);
        return ResponseEntity.ok(deletedBook);
    }


//    @PreAuthorize("hasRole('ADMIN')")
//    @PatchMapping("/admin/delete/books/{id}/")
//    public ResponseEntity<BookAdminResponseDTO> deleteBook(@PathVariable Long id, Map<String, Object> updates ) {
//        BookResponseDTO updatedBook = bookService.pdateBookPartial(id, updates);
//        return ResponseEntity.ok(updatedBook);
//
//    }


}
