package com.aprzybysz.library.config;

import com.aprzybysz.library.data.util.AverageRatingCalculatorImpl;
import com.aprzybysz.library.data.util.IAverageRatingCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.aprzybysz.library")
public class ApplactionConfiguration {

  /**
   * @return a strategy implementation
   * more beans with other strategies can be created and applied to the Service layer
   */
  @Bean
  public IAverageRatingCalculator getStrategy() {
    return new AverageRatingCalculatorImpl();
  }
}
