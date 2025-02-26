package com.pdp.currencyfetcher.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ExecutorService service() {
        return Executors.newFixedThreadPool(10);
    }

}
