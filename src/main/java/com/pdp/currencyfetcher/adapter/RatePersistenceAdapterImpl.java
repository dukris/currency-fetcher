package com.pdp.currencyfetcher.adapter;

import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.adapter.repository.RateRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RatePersistenceAdapterImpl implements RatePersistenceAdapter {

  private final RateRepository repository;
  private final VersionPersistenceAdapter versionPersistenceAdapter;

  @Override
  public List<Rate> findAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public List<Rate> save(List<Rate> rates) {
    List<Rate> saved = repository.saveAll(rates);
    versionPersistenceAdapter.next();
    return saved;
  }

}
