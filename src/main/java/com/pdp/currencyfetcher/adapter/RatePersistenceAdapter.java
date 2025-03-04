package com.pdp.currencyfetcher.adapter;

import com.pdp.currencyfetcher.domain.Rate;
import java.util.List;

public interface RatePersistenceAdapter {

  /**
   * Poll currencies and rates from the database.
   *
   * @return List of currencies and rates
   */
  List<Rate> poll(Long version, Long timeout);

  /**
   * Insert or update provided currencies and rates.
   *
   * @param rates List of currencies and rates
   */
  void upsert(List<Rate> rates);

}
