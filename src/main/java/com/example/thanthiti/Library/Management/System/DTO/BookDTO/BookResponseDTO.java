package com.example.thanthiti.Library.Management.System.DTO.BookDTO;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookResponseDTO {
    private String title;
    private String author;
    private String description;
    public BookResponseDTO() {
    }
    public BookResponseDTO(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }


}
