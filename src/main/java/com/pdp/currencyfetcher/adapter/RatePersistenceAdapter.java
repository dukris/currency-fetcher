package com.pdp.currencyfetcher.adapter;

import com.pdp.currencyfetcher.domain.RateEntity;
import java.util.List;

public interface RatePersistenceAdapter {

  /**
   * Poll currencies and rates from the database.
   *
   * @return List of currencies and rates
   */
  List<RateEntity> poll(Long version, Long timeout);

  /**
   * Stores provided currencies and rates.
   *
   * @param rates List of currencies and rates
   * @return Saved rates
   */
  List<RateEntity> save(List<RateEntity> rates);

}
