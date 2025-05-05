package com.pdp.currencyfetcher.adapter.impl;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import com.pdp.currencyfetcher.adapter.repository.RateRepository;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.exception.NoUpdatedContentException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RatePersistenceAdapterImpl implements RatePersistenceAdapter {

    private final RateRepository repository;
    private final VersionPersistenceAdapter versionPersistenceAdapter;
    private final RateMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Rate> getAll() {
        return mapper.toModel(repository.findAll());
    }

    @Override
    public List<Rate> getByPeriod(String currency, LocalDate from, LocalDate to) {
        return List.of(); // todo implement
    }

    @Override
    @SneakyThrows
    @Transactional(readOnly = true)
    public List<Rate> poll(Long version, Long timeout) {
        if (version < versionPersistenceAdapter.current()) {
            return mapper.toModel(repository.findAll());
        } else {
            long start = System.currentTimeMillis();
            while (System.currentTimeMillis() - start < timeout * 1000) {
                TimeUnit.MILLISECONDS.sleep(500);
                if (version < versionPersistenceAdapter.current()) {
                    return mapper.toModel(repository.findAll());
                }
            }
        }
        throw new NoUpdatedContentException();
    }

    @Override
    @Transactional
    public void upsert(List<Rate> rates) {
        rates.forEach(rate -> repository.upsert(rate.getCurrency(), rate.getValue()));
        versionPersistenceAdapter.next();
    }

}
