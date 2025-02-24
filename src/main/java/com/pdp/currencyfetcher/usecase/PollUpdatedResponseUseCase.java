package com.pdp.currencyfetcher.usecase;

import org.springframework.http.ResponseEntity;

public interface PollUpdatedResponseUseCase {

  /**
   * Fetches currencies and rates using an appropriate API.
   *
   * @return List of currencies and rates
   */
  ResponseEntity fetch(Long version, Long timeout) throws InterruptedException;

}
