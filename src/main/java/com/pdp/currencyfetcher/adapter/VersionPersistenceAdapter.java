package com.pdp.currencyfetcher.adapter;

public interface VersionPersistenceAdapter {

  /**
   * Retrieve the next version.
   *
   * @return the latest version
   */
  Long next();

  /**
   * Retrieve current version.
   *
   * @return the latest version
   */
  Long current();

}
