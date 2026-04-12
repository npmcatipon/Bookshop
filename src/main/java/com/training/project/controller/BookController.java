package com.training.project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.training.project.dto.BookDTO;
import com.training.project.entity.Book;
import com.training.project.exception.ApiResponse;
import com.training.project.exception.BookIdNotFoundException;
import com.training.project.service.BookService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> readBook(){
        List<Book> books = bookService.readBook();
        if (books.isEmpty()) {
            throw new BookIdNotFoundException();
        }
        return ResponseEntity.ok(ApiResponse.success("Success", books));
    }
    
    @PostMapping
    @PreAuthorize("ADMIN")
    public ResponseEntity<ApiResponse<BookDTO>> createBook(@Valid @RequestBody BookDTO bookDto) {
        BookDTO newBookDto = bookService.createBook(bookDto);
        return ResponseEntity.ok(ApiResponse.success("Success", newBookDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(
            "Successfully deleted book.", 
            bookService.deleteBook(id)));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> updateBook(
        @PathVariable Long id,
        @Valid @RequestBody BookDTO bookDto
    ) {
        return ResponseEntity.ok(ApiResponse.success(
            "Successfully Updated the Book.", 
            bookService.updateBook(id, bookDto)));
    }
    


}