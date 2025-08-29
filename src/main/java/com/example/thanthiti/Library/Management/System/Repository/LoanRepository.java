package com.example.thanthiti.Library.Management.System.Repository;

import com.example.thanthiti.Library.Management.System.Entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    Optional<Loan> findByBookIdAndReturnedFalse(Long bookId);
    List<Loan> findByUserId(Long userId);
}
