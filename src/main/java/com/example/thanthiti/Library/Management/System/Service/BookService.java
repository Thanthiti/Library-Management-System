package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookAdminRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookResponseDTO;
import com.example.thanthiti.Library.Management.System.Entity.Book;
import com.example.thanthiti.Library.Management.System.Entity.Category;
import com.example.thanthiti.Library.Management.System.Exeption.ResourceNotFoundException;
import com.example.thanthiti.Library.Management.System.Repository.BookRepository;
import com.example.thanthiti.Library.Management.System.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    public BookService(BookRepository bookRepository,CategoryRepository categoryRepository ) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<BookResponseDTO> getAllBooks() {
        // Logic to get all books
        return bookRepository.findAll().stream()
                .map(book -> {
                    BookResponseDTO bookResponseDTO = new BookResponseDTO();
                    bookResponseDTO.setTitle(book.getTitle());
                    bookResponseDTO.setAuthor(book.getAuthor());
                    bookResponseDTO.setDescription(book.getDescription());
                    return bookResponseDTO;
                })
                .toList();
    }

    public BookResponseDTO getBookById(Long id) {
        // Logic to get book by id
        return bookRepository.findById(id)
                .map(book -> {
                    BookResponseDTO bookResponseDTO = new BookResponseDTO();
                    bookResponseDTO.setTitle(book.getTitle());
                    bookResponseDTO.setAuthor(book.getAuthor());
                    bookResponseDTO.setDescription(book.getDescription());
                    return bookResponseDTO;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    public BookAdminResponseDTO addBook(BookAdminRequestDTO bookAdminRequestDTO){
        Category category = categoryRepository.findById(bookAdminRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Book book = new Book();
        book.setTitle(bookAdminRequestDTO.getTitle());
        book.setAuthor(bookAdminRequestDTO.getAuthor());
        book.setDescription(bookAdminRequestDTO.getDescription());
        book.setCategory(category);
        bookRepository.save(book);
        BookAdminResponseDTO bookAdminResponseDTO = new BookAdminResponseDTO();
        bookAdminResponseDTO.setId(book.getId());
        bookAdminResponseDTO.setTitle(book.getTitle());
        bookAdminResponseDTO.setAuthor(book.getAuthor());
        bookAdminResponseDTO.setDescription(book.getDescription());
        bookAdminResponseDTO.setCategoryName(category.getName());
        return bookAdminResponseDTO;
    }

}
