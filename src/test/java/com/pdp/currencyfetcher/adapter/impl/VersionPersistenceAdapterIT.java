package com.pdp.currencyfetcher.adapter.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import integration.IntegrationTest;
import integration.PostgresIntegration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:sql/init-sequence.sql")
class VersionPersistenceAdapterIT extends IntegrationTest implements PostgresIntegration {

  @Autowired
  private VersionPersistenceAdapter adapter;

  @Test
  void shouldReturnCurrentVersion() {
    // when
    Long actual = adapter.current();

    // then
    assertNotNull(actual);
  }

  @Test
  void shouldReturnNextVersion() {
    // when
    Long actual = adapter.next();

    // then
    assertNotNull(actual);
  }

}
