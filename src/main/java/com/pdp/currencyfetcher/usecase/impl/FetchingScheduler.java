package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.usecase.FetchRatesUseCase;
import com.pdp.currencyfetcher.usecase.FetchRatesUseCase.RateData;
import com.pdp.currencyfetcher.usecase.FetchRatesUseCase.RateDataMapper;
import com.pdp.currencyfetcher.usecase.SaveCurrencyUseCase;
import com.pdp.currencyfetcher.usecase.ScheduleFetchingUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchingScheduler implements ScheduleFetchingUseCase {

  private final FetchRatesUseCase fetcher;
  private final SaveCurrencyUseCase saver;
  private final RateDataMapper mapper;

  @Override
  @Scheduled(fixedDelay = 1000000000)
  public void schedule() {
    List<RateData> rates = fetcher.fetch();
    saver.save(mapper.toEntity(rates));
  }
}
