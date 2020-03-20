package com.aprzybysz.library.api.mapper;

import com.aprzybysz.library.api.dto.AuthorRatingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Map;

@Mapper(componentModel = "spring")
public interface AuthorRatingMapper {

  @Mappings({
      @Mapping(target="author", expression = "java(source.getKey())"),
      @Mapping(target="averageRating", expression = "java(source.getValue())") })
  AuthorRatingDTO ratingMapToAuthorRatingDTO(Map.Entry<String, Double> source);

}
