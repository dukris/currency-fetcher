package com.pdp.currencyfetcher.adapter;

import com.pdp.currencyfetcher.domain.RateEntity;
import java.util.List;

public interface RatePersistenceAdapter {

  /**
   * Find all currencies and rates from the database.
   *
   * @return List of currencies and rates
   */
  List<RateEntity> findAll();

  /**
   * Poll currencies and rates from the database.
   *
   * @return List of currencies and rates
   */
  List<RateEntity> poll(Long version, Long timeout);

  /**
   * Insert or update provided currencies and rates.
   *
   * @param rates List of currencies and rates
   */
  void upsert(List<RateEntity> rates);

}
