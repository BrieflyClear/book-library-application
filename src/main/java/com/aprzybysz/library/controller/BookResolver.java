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
        return new Book.BookBuilder()
            .isbn("null")
            .title("null")
            .create();
      }
      return null;
  }
}