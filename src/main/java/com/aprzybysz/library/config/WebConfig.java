package com.aprzybysz.library.config;

import com.aprzybysz.library.config.formatters.BookFormatter;
import com.aprzybysz.library.config.formatters.DateFormatter;
import com.aprzybysz.library.controller.BookResolver;
import com.aprzybysz.library.data.model.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.aprzybysz.library" })
public class WebConfig implements ApplicationContextAware, WebMvcConfigurer {

  private ApplicationContext applicationContext;

  public WebConfig() {
    super();
  }

  public void setApplicationContext(final ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  /* ******************************************************************* */
  /*  GENERAL CONFIGURATION ARTIFACTS                                    */
  /*  Static Resources, i18n Messages, Formatters (Conversion Service)   */
  /* ******************************************************************* */

  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    WebMvcConfigurer.super.addResourceHandlers(registry);
    registry.addResourceHandler("/images/**").addResourceLocations("/images/");
    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
  }

  @Bean
  public ResourceBundleMessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("Messages");
    return messageSource;
  }

  @Override
  public void addFormatters(final FormatterRegistry registry) {
    WebMvcConfigurer.super.addFormatters(registry);
    registry.addFormatter(bookFormatter());
    registry.addFormatter(dateFormatter());
  }

  @Bean
  public BookFormatter bookFormatter() {
    return new BookFormatter();
  }

  @Bean
  public DateFormatter dateFormatter() {
    return new DateFormatter();
  }

  /* **************************************************************** */
  /*  THYMELEAF-SPECIFIC ARTIFACTS                                    */
  /*  TemplateResolver <- TemplateEngine <- ViewResolver              */
  /* **************************************************************** */

  @Bean
  public SpringResourceTemplateResolver templateResolver(){
    // SpringResourceTemplateResolver automatically integrates with Spring's own
    // resource resolution infrastructure, which is highly recommended.
    SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
    templateResolver.setApplicationContext(this.applicationContext);
    templateResolver.setPrefix("/WEB-INF/templates/");
    templateResolver.setSuffix(".html");
    // HTML is the default value, added here for the sake of clarity.
    templateResolver.setTemplateMode(TemplateMode.HTML);
    // Template cache is true by default. Set to false if you want
    // templates to be automatically updated when modified.
    templateResolver.setCacheable(true);
    return templateResolver;
  }

  @Bean
  public BookResolver bookResolver() {
    return new BookResolver();
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(bookResolver());
  }

  @Bean
  public SpringTemplateEngine templateEngine(){
    // SpringTemplateEngine automatically applies SpringStandardDialect and
    // enables Spring's own MessageSource message resolution mechanisms.
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
    // speed up execution in most scenarios, but might be incompatible
    // with specific cases when expressions in one template are reused
    // across different data types, so this flag is "false" by default
    // for safer backwards compatibility.
    templateEngine.setEnableSpringELCompiler(true);
    return templateEngine;
  }

  @Bean
  public ThymeleafViewResolver viewResolver(){
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    return viewResolver;
  }
}