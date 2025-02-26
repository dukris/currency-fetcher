package com.pdp.currencyfetcher.adapter;

public interface VersionPersistenceAdapter {

  /**
   * Retrieves the next version.
   *
   * @return the latest version
   */
  Long next();

  /**
   * Retrieves current version.
   *
   * @return the latest version
   */
  Long current();

}
