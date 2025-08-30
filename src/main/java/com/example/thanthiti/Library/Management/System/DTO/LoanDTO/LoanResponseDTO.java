package com.example.thanthiti.Library.Management.System.DTO.LoanDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanResponseDTO {


    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean returned;
    private String bookName;
    private String userName;
    private String message;


    public LoanResponseDTO() {

    }

    public LoanResponseDTO(String userName,String bookName,LocalDate loanDate,LocalDate returnDate,boolean returned){
        this.userName = userName;
        this.bookName = bookName;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.returned = returned;
    }

    public LoanResponseDTO(String userName,String bookName,LocalDate loanDate,LocalDate returnDate,boolean returned,String message){
        this.userName = userName;
        this.bookName = bookName;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.returned = returned;
        this.message = message;
    }



//    get/set method

    public LocalDate getLoanDate() {
        return loanDate;
    }
    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public boolean isReturned() {
        return returned;
    }
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }
    public String getBookName() {
        return bookName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }


}
