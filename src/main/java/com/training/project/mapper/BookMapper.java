package com.training.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.training.project.dto.BookDTO;
import com.training.project.entity.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    Book toEntity(BookDTO bookDto);

    BookDTO toDTO(Book book);

}
