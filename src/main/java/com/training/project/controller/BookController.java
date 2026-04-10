package com.training.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.project.dto.BookDTO;
import com.training.project.exception.ApiResponse;
import com.training.project.service.BookService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createBook(@Valid @RequestBody BookDTO bookDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(
                    ApiResponse.of(HttpStatus.CREATED.value(), 
                    "Successfully created a book. " + 
                    bookService.createBook(bookDto).toString())
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(
                    ApiResponse.of(
                        HttpStatus.OK.value(), 
                    "Successfully deleted " +
                    bookService.deleteBook(id)));
    }
    


}