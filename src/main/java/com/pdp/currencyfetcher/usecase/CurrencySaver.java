package com.pdp.currencyfetcher.usecase;

import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.repository.RateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencySaver implements SaveCurrencyUseCase {

  private final RateRepository repository;

  @Override
  public List<Rate> save(List<Rate> rates) {
    return repository.saveAll(rates);
  }
}
