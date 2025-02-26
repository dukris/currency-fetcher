package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.gateway.BinanceGateway;
import com.pdp.currencyfetcher.usecase.ScheduleFetchingUseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FetchingScheduler implements ScheduleFetchingUseCase {

  private final RateMapper mapper;
  private final BinanceGateway gateway;
  private final RatePersistenceAdapter ratePersistenceAdapter;

  @Override
  @Scheduled(cron = "* * * * * *")
  @SchedulerLock(name = "fetchRatesLock", lockAtLeastFor = "PT1S", lockAtMostFor = "PT5S")
  public void schedule() {
    ratePersistenceAdapter.save(mapper.toEntity(gateway.getAll()));
    log.debug("Fetching rates has been completed: {}", LocalDateTime.now());
  }

}
