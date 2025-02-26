package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.gateway.BinanceGateway;
import com.pdp.currencyfetcher.gateway.BinanceGateway.RateData;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.usecase.ScheduleFetchingUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FetchingScheduler implements ScheduleFetchingUseCase {

  private final RateMapper mapper;
  private final BinanceGateway gateway;
  private final RatePersistenceAdapter ratePersistenceAdapter;

  @Override
// @Scheduled(cron = "0 * * * * *")
  @Scheduled(fixedRate = 50000000)
  public void schedule() {
    List<RateData> rates = gateway.getAll();
    ratePersistenceAdapter.save(mapper.toEntity(rates));
  }
}
