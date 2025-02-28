package com.pdp.currencyfetcher.adapter;

import com.pdp.currencyfetcher.adapter.repository.RateRepository;
import com.pdp.currencyfetcher.domain.Rate;
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
  @SneakyThrows
  public List<Rate> poll(Long version, Long timeout) {
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
  public List<Rate> save(List<Rate> rates) {
    List<Rate> saved = repository.saveAll(rates);
    versionPersistenceAdapter.next();
    return saved;
  }

}
