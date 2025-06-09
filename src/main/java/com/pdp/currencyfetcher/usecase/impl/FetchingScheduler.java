package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.gateway.BinanceGateway;
import com.pdp.currencyfetcher.usecase.ScheduleFetchingUseCase;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FetchingScheduler implements ScheduleFetchingUseCase {

    private final RateMapper mapper;
    private final BinanceGateway gateway;
    private final RatePersistenceAdapter ratePersistenceAdapter;

    @Override
//    @Scheduled(cron = "* * * * * *")
    @SchedulerLock(name = "fetchRatesLock")
    public void schedule() {
        ratePersistenceAdapter.save(mapper.toModels(gateway.getAll()));
        log.info("Fetching rates has been completed: {}", LocalDateTime.now());
    }

}
