package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.usecase.RemoveOutdatedRatesUseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RemovingScheduler implements RemoveOutdatedRatesUseCase {

  private final RatePersistenceAdapter ratePersistenceAdapter;

  @Override
  @Scheduled(cron = "0 0 0 1 * ?")
  @SchedulerLock(name = "clearRatesLock")
  public void remove() {
    LocalDateTime now = LocalDateTime.now();
    ratePersistenceAdapter.deleteByDateBefore(now);
    log.info("Outdated rates have been removed: {}", now);
  }
}
