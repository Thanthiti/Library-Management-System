package com.example.thanthiti.Library.Management.System.DTO.LoanDTO;

import jakarta.validation.constraints.NotNull;

public class LoanRequestDTO {

    @NotNull(message = "Book ID is required")
    private long bookId;
    @NotNull(message = "User ID is required")
    private long userId;

    public LoanRequestDTO() {

    }

    public long getBookId() {
        return bookId;
    }
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

}
