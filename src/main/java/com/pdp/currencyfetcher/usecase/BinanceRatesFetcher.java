package com.pdp.currencyfetcher.usecase;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class BinanceRatesFetcher implements FetchRatesUseCase {

  private final RestTemplate template;
  private static final String BINANCE_API_URL = "https://api.binance.com/api/v3/ticker/price";

  @Override
  public List<RateData> fetch() {
    return Arrays.asList(
        Objects.requireNonNull(template.getForObject(BINANCE_API_URL, RateData[].class))
    );
  }

}
