package com.pdp.currencyfetcher.adapter;

import com.pdp.currencyfetcher.domain.RateEntity;
import java.util.List;

public interface RatePersistenceAdapter {

  /**
   * Retrieves provided currencies and rates from the database.
   *
   * @return List of currencies and rates
   */
  List<RateEntity> findAll();

  /**
   * Stores provided currencies and rates.
   *
   * @param rates List of currencies and rates
   * @return Saved rates
   */
  List<RateEntity> save(List<RateEntity> rates);

}
