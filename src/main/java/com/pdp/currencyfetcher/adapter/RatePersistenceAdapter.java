package com.pdp.currencyfetcher.adapter;

import com.pdp.currencyfetcher.domain.Rate;
import java.time.LocalDateTime;
import java.util.List;

public interface RatePersistenceAdapter {

    /**
     * Retrieve all currencies and rates from the database.
     *
     * @return List of currencies and rates
     */
    List<Rate> getAll();

    /**
     * Retrieve currencies and rates by period of time.
     *
     * @return List of currencies and rates
     */
    List<Rate> getByPeriod(String currency, LocalDateTime from, LocalDateTime to);

    /**
     * Poll currencies and rates from the database.
     *
     * @return List of currencies and rates
     */
    List<Rate> poll(Long version, Long timeout);

    /**
     * Save provided currencies and rates.
     *
     * @param rates List of currencies and rates
     */
    void save(List<Rate> rates);

    /**
     * Delete outdated rates based on the provided date and time.
     *
     * @param date Date and time to removed outdated rates
     */
    void deleteByDateBefore(LocalDateTime date);

}
