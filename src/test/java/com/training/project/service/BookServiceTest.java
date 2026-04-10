package com.training.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
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
    Book book2;
    List<Book> books;
    BookDTO bookDto;

    @BeforeEach
    void setup() {

        book = new Book("sample 1", "author 1");
        book.setId(1L);
        book2 = new Book("sample 2", "author 2");

        books = List.of(book, book2);

    }

    @Test
    void createBook_successful() {

        bookDto = new BookDTO("sample 1", "author 1");

        when(bookRepository.existsByTitleAndAuthor(book.getTitle(), book.getAuthor()))
            .thenReturn(false);

        when(bookMapper.toEntity(any(BookDTO.class))).thenReturn(book);

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        when(bookMapper.toDTO(any(Book.class))).thenReturn(bookDto);
        
        BookDTO result = bookService.createBook(bookDto);
        
        assertNotNull(result);
        assertEquals("sample 1", result.getTitle());
        assertEquals("author 1", result.getAuthor());

        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).save(book);
    }

    @Test
    public void createBook_failed_duplicateBookTitleAndAuthor() {
        bookDto = new BookDTO(
            "sample 1", 
            "author 1");
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

        assertNull(dto);

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

    @Test
    public void readBook_success() {
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> result = bookService.readBook();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("sample 1", result.get(0).getTitle());
        assertEquals("sample 2", result.get(1).getTitle());

        verify(bookRepository).findAll();
    }

    @Test
    public void readBook_failed_noBooksAvailable() {
        when(bookRepository.findAll()).thenReturn(List.of());

        List<Book> result = bookService.readBook();

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(bookRepository).findAll();
        
    }

    @Test
    public void updateBook_success() {
        Long id = 1L;
        bookDto = new BookDTO(
                        "The new title", 
                        "The new author");

        when(bookRepository.findById(id))
                .thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(bookMapper.toDTO(book))
                .thenReturn(bookDto);

        BookDTO result = bookService.updateBook(id, bookDto);

        assertNotNull(result);
        assertEquals("The new title", result.getTitle());
        assertEquals("The new author", result.getAuthor());

        verify(bookRepository).findById(id);
        verify(bookRepository).save(book);
        verify(bookMapper).toDTO(book);
    }
}
