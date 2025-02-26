package com.pdp.currencyfetcher.adapter;

import com.pdp.currencyfetcher.domain.Rate;
import java.util.List;

public interface RatePersistenceAdapter {

  /**
   * Retrieves provided currencies and rates from the database.
   *
   * @return List of currencies and rates
   */
  List<Rate> findAll();

  /**
   * Stores provided currencies and rates.
   *
   * @param rates List of currencies and rates
   * @return Saved rates
   */
  List<Rate> save(List<Rate> rates);

}
