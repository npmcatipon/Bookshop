package com.training.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.project.dto.BookDTO;
import com.training.project.entity.Book;
import com.training.project.exception.ApiResponse;
import com.training.project.service.BookService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> readBook(){
        return ResponseEntity
                .status(HttpStatus.OK.value())
                .body(bookService.readBook());
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse> createBook(@Valid @RequestBody BookDTO bookDto) {
        BookDTO newBookDto = bookService.createBook(bookDto);
        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .body(
                    ApiResponse.of(HttpStatus.CREATED.value(), 
                    "Successfully created a book. " + 
                    newBookDto.toString())
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