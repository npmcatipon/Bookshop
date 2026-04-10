package com.training.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.project.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    
    Boolean existsByTitleAndAuthor(String title, String author);
}
