package com.pdp.currencyfetcher.usecase;

import com.pdp.currencyfetcher.domain.Rate;
import java.util.List;

public interface SaveCurrencyUseCase {

  List<Rate> save(List<Rate> rates);

}
