package com.pdp.currencyfetcher.usecase;

import com.pdp.currencyfetcher.domain.Rate;
import java.util.List;

public interface SaveCurrencyUseCase {

  /**
   * Stores provided currencies and rates.
   *
   * @param rates List of currencies and rates
   * @return Saved rates
   */
  List<Rate> save(List<Rate> rates);

}
