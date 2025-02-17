package com.pdp.currencyfetcher.scheduler;

import com.pdp.currencyfetcher.usecase.FetchRatesUseCase;
import com.pdp.currencyfetcher.usecase.FetchRatesUseCase.RateData;
import com.pdp.currencyfetcher.usecase.SaveCurrencyUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyScheduler implements Scheduler {

  private final FetchRatesUseCase fetcher;
  private final SaveCurrencyUseCase saver;

  @Override
  @Scheduled(fixedDelay = 1000000000)
  public void schedule() {
    List<RateData> rates = fetcher.fetch();
    // TODO map to entity and save
  }

}
