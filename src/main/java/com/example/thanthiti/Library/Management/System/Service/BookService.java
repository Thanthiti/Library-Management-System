package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookAdminRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookAdminResponseDTO;
import com.example.thanthiti.Library.Management.System.DTO.BookDTO.BookResponseDTO;
import com.example.thanthiti.Library.Management.System.Entity.Book;
import com.example.thanthiti.Library.Management.System.Entity.Category;
import com.example.thanthiti.Library.Management.System.Exeption.ResourceNotFoundException;
import com.example.thanthiti.Library.Management.System.Mapper.BookMapper;
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
                .map(BookMapper::toBookResponseDTO)
                .toList();
    }

    public BookResponseDTO getBookById(Long id) {
        // Logic to get book by id
        return bookRepository.findById(id)
                .map(BookMapper::toBookResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    public BookAdminResponseDTO addBook(BookAdminRequestDTO bookAdminRequestDTO){
        Category category = categoryRepository.findById(bookAdminRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Book book = BookMapper.toBook(bookAdminRequestDTO);
        book.setCategory(category);
        Book saved = bookRepository.save(book);
        return BookMapper.toBookAdminResponseDTO(saved);
    }

    public BookAdminResponseDTO updateBook(long id, BookAdminRequestDTO bookAdminRequestDTO){
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        Book updateBook = BookMapper.toBook(bookAdminRequestDTO);
        // set category
        Category category = categoryRepository.findById(bookAdminRequestDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        updateBook.setCategory(category);

        Book saved = bookRepository.save(book);

        return BookMapper.toBookAdminResponseDTO(saved);
    }

//    Hard Delete Book
    public BookAdminResponseDTO deleteBook(long id){
        Book book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));

        bookRepository.delete(book);
        return BookMapper.toBookAdminResponseDTO(book);
    }

}
