package com.pdp.currencyfetcher.usecase;

import com.pdp.currencyfetcher.domain.Rate;

public interface SaveCurrencyUseCase {

  Rate save(Rate rate);

}
