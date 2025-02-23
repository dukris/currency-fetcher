package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.repository.RateRepository;
import com.pdp.currencyfetcher.usecase.GenerateVersionUseCase;
import com.pdp.currencyfetcher.usecase.RetrieveCurrencyUseCase;
import com.pdp.currencyfetcher.usecase.SaveCurrencyUseCase;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostgresCurrencyHandler implements SaveCurrencyUseCase, RetrieveCurrencyUseCase {

    private final RateRepository repository;
    private final GenerateVersionUseCase generator;

    @Override
    @Transactional
    public List<Rate> save(List<Rate> rates) {
        List<Rate> saved = repository.saveAll(rates);
        generator.next();
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rate> getAll() {
        return repository.findAll();
    }

}
