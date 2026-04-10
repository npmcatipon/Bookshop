package com.training.project.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class BookDTO {

    @NotBlank(message = "Title must not be blank.")
    private String title;

    @NotBlank(message = "Author must not be blank.")
    private String author;

    private LocalDate dateCreated;
    
    public BookDTO() {
    }

    public BookDTO(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "BookDTO [title=" + title + ", author=" + author + ", dateCreated=" + dateCreated + "]";
    }

}
