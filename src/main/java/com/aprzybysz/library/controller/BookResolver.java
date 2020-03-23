package com.aprzybysz.library.controller;

import com.aprzybysz.library.data.model.Book;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class BookResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter methodParameter) {
    return methodParameter.getParameterAnnotation(BookObject.class) != null;
  }

  @Override
  public Object resolveArgument(
      MethodParameter methodParameter,
      ModelAndViewContainer modelAndViewContainer,
      NativeWebRequest nativeWebRequest,
      WebDataBinderFactory webDataBinderFactory) throws Exception {

      if(Book.class.equals(methodParameter.getParameterType())) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) nativeWebRequest;
        HttpServletRequest request = servletWebRequest.getRequest();

        String isbn = request.getParameter("isbn");
        String title = request.getParameter("title");
        String subtitle = request.getParameter("subtitle");
        String publisher = request.getParameter("publisher");
        Long publishedDateInUnix = Long.valueOf(request.getParameter("publishedDate"));
        String description = request.getParameter("description");
        Integer pageCount = Integer.parseInt(request.getParameter("pageCount"));
        String thumbnailUrl = request.getParameter("thumbnailUrl");
        String language = request.getParameter("language");
        Double averageRating = Double.valueOf(request.getParameter("averageRating"));
        Integer ratingCount = Integer.parseInt(request.getParameter("ratingCount"));
        String previewLink = request.getParameter("previewLink");
        return new Book.BookBuilder()
            .isbn(isbn)
            .title(title)
            .subtitle(subtitle)
            .publisher(publisher)
            .publishedDate(publishedDateInUnix)
            .description(description)
            .pageCount(pageCount)
            .thumbnailUrl(thumbnailUrl)
            .language(language)
            .averageRating(averageRating)
            .ratingsCount(ratingCount)
            .previewLink(previewLink)
            .authors(null)
            .categories(null)
            .create();
      }
      return null;
  }
}