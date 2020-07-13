package com.rosato.spielepalast.backend.config;

import com.rosato.spielepalast.backend.models.Facility;
import com.rosato.spielepalast.backend.models.Facility.EntityType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource(value = "classpath:/application.properties")
@ComponentScan(basePackages = { "com.rosato.spielepalast.backend" })
public class MyConfig {
  @Bean(name = "facility")
  public Facility facility() {
    Facility facility = new Facility();
    facility.addEntity(EntityType.POSTMAN);

    return facility;
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
      }
    };
  }
}
