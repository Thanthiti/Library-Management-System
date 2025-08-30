package com.example.thanthiti.Library.Management.System.Mapper;

import com.example.thanthiti.Library.Management.System.DTO.LoanDTO.LoanResponseDTO;
import com.example.thanthiti.Library.Management.System.Entity.Loan;

public class LoanMapper {

    //  getMyLoan, getAllLoan
    public static LoanResponseDTO toLoanResponseDTO(Loan loan) {
        System.out.println("1 ");

        return new LoanResponseDTO(
                loan.getUser().getName(),
                loan.getBook().getTitle(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.isReturned()
        );
    }

    // Create loan or return loan
    public static LoanResponseDTO toLoanResponseDTOs(Loan loan, String message) {
        System.out.println("2");
        return new LoanResponseDTO(
                loan.getUser().getName(),
                loan.getBook().getTitle(),
                loan.getLoanDate(),
                loan.getReturnDate(),
                loan.isReturned(),
                message
        );
    }


}