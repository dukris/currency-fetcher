package com.pdp.currencyfetcher.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

  @Value("${spring.application.version}")
  private String version;
  @Value("${spring.application.name}")
  private String name;

  @Bean
  public ExecutorService service() {
    return Executors.newFixedThreadPool(10);
  }

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info(
        new Info().title(name).version(version)
    );
  }

}
