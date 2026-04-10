package com.training.project.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.training.project.dto.BookDTO;
import com.training.project.entity.Book;
import com.training.project.exception.BookIdNotFoundException;
import com.training.project.exception.DuplicateBookException;
import com.training.project.mapper.BookMapper;
import com.training.project.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
@Validated
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    /**
     * Create a {@link Book} and save to database. 
     * 
     * @param bookDto to create; must not be {@code null}
     * @return the created book as a {@link Book}
     * @throws {@link DuplicateBookException} if {@code title} and {@code author} exists in database.
     */
    @Transactional
    public Book createBook(BookDTO bookDto) {
        if (bookRepository.existsByTitleAndAuthor(bookDto.getTitle(),bookDto.getAuthor())) {
                throw new DuplicateBookException();
        }

        Book book = bookRepository.save(bookMapper.toEntity(bookDto));

        return book;
    }

    /**
     * Delete a book.
     * 
     * @param id of the book to be deleted
     * @return {@link BookDTO} of the deleted book
     * @throws {@link BookIdNotFoundException} when Book ID is not existing
     */
    @Transactional
    public BookDTO deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                    .orElseThrow(() -> new BookIdNotFoundException());
        bookRepository.delete(book);
        return bookMapper.toDTO(book);
    }
}
