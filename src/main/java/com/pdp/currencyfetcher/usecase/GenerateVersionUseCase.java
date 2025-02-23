package com.pdp.currencyfetcher.usecase;

public interface GenerateVersionUseCase {

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