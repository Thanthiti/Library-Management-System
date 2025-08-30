package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.DTO.LoanDTO.LoanRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.LoanDTO.LoanResponseDTO;
import com.example.thanthiti.Library.Management.System.Entity.Book;
import com.example.thanthiti.Library.Management.System.Entity.Loan;
import com.example.thanthiti.Library.Management.System.Entity.User;
import com.example.thanthiti.Library.Management.System.Mapper.LoanMapper;
import com.example.thanthiti.Library.Management.System.Repository.BookRepository;
import com.example.thanthiti.Library.Management.System.Repository.LoanRepository;
import com.example.thanthiti.Library.Management.System.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public LoanResponseDTO createLoan(LoanRequestDTO loanRequestDTO, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(loanRequestDTO.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        loanRepository.findByBookIdAndReturnedFalse(book.getId()).ifPresent(loan -> {
            throw new RuntimeException("Book already loaned");
        });

        Loan loan = Loan.createNewLoan(user, book);
        loanRepository.save(loan);

        return LoanMapper.toLoanResponseDTOs(loan,"Loan created successfully");
    }

    public LoanResponseDTO returnLoan(Long bookId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

       Loan loan = loanRepository.findByBookIdAndReturnedFalse(book.getId()).orElseThrow(() -> new RuntimeException("No active loan for this book "));
       loan.returnBook();
       loanRepository.save(loan);

        return LoanMapper.toLoanResponseDTOs(loan, "Loan returned successfully");
    }

    public List<LoanResponseDTO> getMyLoan(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        List<Loan> loans = loanRepository.findByUserId(user.getId());
        if (loans.isEmpty()) {
            throw new RuntimeException("No loans found for this user");
        }

        return loans.stream().map(LoanMapper::toLoanResponseDTO).toList();
    }

    public List<LoanResponseDTO> getAllLoan() {
        List<Loan> loans = loanRepository.findAll();
        if (loans.isEmpty()) {
            throw new RuntimeException("No loans found");
        }
        return loans.stream().map(LoanMapper::toLoanResponseDTO).toList();
    }
}
