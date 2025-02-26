package com.pdp.currencyfetcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CurrencyFetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyFetcherApplication.class, args);
	}

}
