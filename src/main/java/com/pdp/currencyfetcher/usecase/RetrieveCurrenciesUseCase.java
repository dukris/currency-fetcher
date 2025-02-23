package com.pdp.currencyfetcher.usecase;

import com.pdp.currencyfetcher.domain.Rate;
import java.util.List;

public interface RetrieveCurrenciesUseCase {

    /**
     * Retrieves provided currencies and rates from the database
     *
     * @return List of currencies and rates
     */
    List<Rate> getAll();

}
