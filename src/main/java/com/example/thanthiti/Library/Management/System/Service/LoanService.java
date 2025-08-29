package com.example.thanthiti.Library.Management.System.Service;

import com.example.thanthiti.Library.Management.System.DTO.LoanDTO.LoanRequestDTO;
import com.example.thanthiti.Library.Management.System.DTO.LoanDTO.LoanResponseDTO;
import com.example.thanthiti.Library.Management.System.Entity.Book;
import com.example.thanthiti.Library.Management.System.Entity.Loan;
import com.example.thanthiti.Library.Management.System.Entity.User;
import com.example.thanthiti.Library.Management.System.Repository.BookRepository;
import com.example.thanthiti.Library.Management.System.Repository.LoanRepository;
import com.example.thanthiti.Library.Management.System.Repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setUser(user);
        loan.setLoanDate(LocalDate.now());
        loan.setReturned(false);

        loanRepository.save(loan);

        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        loanResponseDTO.setUserName(user.getName());
        loanResponseDTO.setBookName(book.getTitle());
        loanResponseDTO.setLoanDate(loan.getLoanDate());
        loanResponseDTO.setReturned(loan.isReturned());
        loanResponseDTO.setMessage("Loan successful"); ;

        return loanResponseDTO;
    }
//    {
//        "id": null,
//            "bookId": null,
//            "userId": null,
//            "loanDate": "2025-08-28",
//            "returnDate": null,
//            "returned": false,
//            "bookName": "Things Fall Apart 92003",
//            "userName": "beer",
//            "message": null
//    }

    public LoanResponseDTO returnLoan(Long bookId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

       Loan loan = loanRepository.findByBookIdAndReturnedFalse(book.getId()).orElseThrow(() -> new RuntimeException("No active loan for this book "));
        loan.setReturned(true);
        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);

        LoanResponseDTO loanResponseDTO = new LoanResponseDTO();
        loanResponseDTO.setUserName(user.getName());
        loanResponseDTO.setBookName(loan.getBook().getTitle());
        loanResponseDTO.setLoanDate(loan.getLoanDate());
        loanResponseDTO.setReturned(loan.isReturned());
        loanResponseDTO.setMessage("Loan returned successfully");

        return loanResponseDTO;
    }
//    {
//    "id": null,
//    "bookId": null,
//    "userId": null,
//    "loanDate": "2025-08-29",
//    "returnDate": null,
//    "returned": true,
//    "bookName": "Things Fall Apart 92003",
//    "userName": "beer",
//    "message": "Loan returned successfully"
//}

    public List<LoanResponseDTO> getMyLoan(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        List<Loan> loans = loanRepository.findByUserId(user.getId());
        if (loans.isEmpty()) {
            throw new RuntimeException("No loans found for this user");
        }

        return loans.stream().map(loan -> {
            LoanResponseDTO dto = new LoanResponseDTO();
            dto.setUserName(user.getName());
            dto.setBookName(loan.getBook().getTitle());
            dto.setLoanDate(loan.getLoanDate());
            dto.setReturned(loan.isReturned());
            return dto;
        }).toList();
    }
//        "id": null,
//                "bookId": null,
//                "userId": null,
//                "loanDate": "2025-08-29",
//                "returnDate": null,
//                "returned": false,
//                "bookName": "The Heart Is Deceitful Above All Things 25679",
//                "userName": "beer",
//                "message": null

    public List<LoanResponseDTO> getAllLoan() {
        List<Loan> loans = loanRepository.findAll();
        if (loans.isEmpty()) {
            throw new RuntimeException("No loans found");
        }
        return loans.stream().map(loan -> {
            LoanResponseDTO dto = new LoanResponseDTO();
            dto.setUserName(loan.getUser().getName());
            dto.setBookName(loan.getBook().getTitle());
            dto.setLoanDate(loan.getLoanDate());
            dto.setReturned(loan.isReturned());
            return dto;
        }).toList();
    }


}
