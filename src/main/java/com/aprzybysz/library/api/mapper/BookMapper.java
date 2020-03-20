package com.aprzybysz.library.api.mapper;

import com.aprzybysz.library.api.dto.BookDTO;
import com.aprzybysz.library.data.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

  BookDTO bookToBookDTO(Book entity);
  Book bookDTOToBook(BookDTO dto);
}
