package com.pdp.currencyfetcher.adapter.impl;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import com.pdp.currencyfetcher.adapter.repository.RateRepository;
import com.pdp.currencyfetcher.domain.RateEntity;
import com.pdp.currencyfetcher.exception.NoUpdatedContentException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RatePersistenceAdapterImpl implements RatePersistenceAdapter {

  private final RateRepository repository;
  private final VersionPersistenceAdapter versionPersistenceAdapter;

  @Override
  @Transactional(readOnly = true)
  public List<RateEntity> findAll() {
    return repository.findAll();
  }

  @Override
  @SneakyThrows
  @Transactional(readOnly = true)
  public List<RateEntity> poll(Long version, Long timeout) {
    if (version < versionPersistenceAdapter.current()) {
      return repository.findAll();
    } else {
      long start = System.currentTimeMillis();
      while (System.currentTimeMillis() - start < timeout * 1000) {
        TimeUnit.MILLISECONDS.sleep(500);
        if (version < versionPersistenceAdapter.current()) {
          return repository.findAll();
        }
      }
    }
    throw new NoUpdatedContentException();
  }

  @Override
  @Transactional
  public void upsert(List<RateEntity> rates) {
    rates.forEach(rate -> repository.upsert(rate.getCurrency(), rate.getValue()));
    versionPersistenceAdapter.next();
  }

}
