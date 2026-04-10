package com.training.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.training.project.dto.BookDTO;
import com.training.project.entity.Book;
import com.training.project.exception.BookIdNotFoundException;
import com.training.project.exception.DuplicateBookException;
import com.training.project.mapper.BookMapper;
import com.training.project.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    Book book;
    BookDTO bookDto;

    @BeforeEach
    void setup() {

        book = new Book("sample", "author");
        bookDto = new BookDTO("sample", "author");

    }

    @Test
    void createBook_successful() {

        when(bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor()))
            .thenReturn(false);

        when(bookMapper.toEntity(any(BookDTO.class))).thenReturn(book);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        
        Book result = bookService.createBook(bookDto);
        
        assertNotNull(result);
        assertEquals("sample", result.getTitle());
        assertEquals("author", result.getAuthor());

        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).save(book);
    }

    @Test
    public void createBook_faild_duplicateBookTitleAndAuthor() {
        BookDTO bookDto = new BookDTO(
            "sample", 
            "author");
        when(bookRepository.existsByTitleAndAuthor(
                book.getTitle(), 
                book.getAuthor()
            )
        ).thenReturn(true);

        assertThrows(DuplicateBookException.class, 
            () -> bookService.createBook(bookDto)
        );
    }

    @Test
    public void deleteBook_successful() {
        when(bookRepository.findById(book.getId()))
            .thenReturn(Optional.of(book));
        doNothing().when(bookRepository).
            delete(book);
        when(bookMapper.toDTO(book)).
            thenReturn(bookDto);

        BookDTO dto = bookService.deleteBook(book.getId());

        assertNotNull(dto);

        verify(bookRepository).findById(book.getId());
        verify(bookRepository).delete(book);
        verify(bookMapper).toDTO(book);
    }

    @Test
    public void deleteBook_failed_bookIdNotFound() {
        Long id = 2L;
        when(bookRepository.findById(id))
            .thenReturn(Optional.empty());
        
        assertThrows(BookIdNotFoundException.class, 
            () -> bookService.deleteBook(id)
        );

        verify(bookRepository, never()).delete(any());
        verify(bookMapper, never()).toDTO(book);
    }
}
