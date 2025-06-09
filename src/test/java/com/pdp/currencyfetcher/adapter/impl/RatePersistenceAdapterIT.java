package com.pdp.currencyfetcher.adapter.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.adapter.repository.RateRepository;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.domain.RateEntity;
import integration.IntegrationTest;
import integration.PostgresIntegration;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class RatePersistenceAdapterIT extends IntegrationTest implements PostgresIntegration {

  @Autowired
  private RatePersistenceAdapter adapter;
  @Autowired
  private RateRepository repository;

  @Test
  @Sql("classpath:sql/init-sequence.sql")
  void shouldSaveRates() {
    // given
    Rate expected = new Rate();
    expected.setCurrency("USDT");
    expected.setValue(new BigDecimal("1.0000000000"));

    // when
    adapter.save(List.of(expected));
    List<RateEntity> actual = repository.findLatestRates();

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected.getCurrency(), actual.get(0).getCurrency());
    assertEquals(expected.getValue(), actual.get(0).getValue());
  }

  @Test
  @Sql(scripts = {"classpath:sql/init-sequence.sql", "classpath:sql/insert-rate.sql"})
  void shouldPollActualRates() {
    // given
    Rate expected = new Rate();
    expected.setCurrency("USDT");
    expected.setValue(new BigDecimal("1.0000000000"));

    // when
    List<Rate> actual = adapter.poll(0L, 0L);

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected.getCurrency(), actual.get(0).getCurrency());
    assertEquals(expected.getValue(), actual.get(0).getValue());
  }

  @Test
  @Sql(scripts = {"classpath:sql/init-sequence.sql", "classpath:sql/insert-rate.sql"})
  void shouldReturnAllRates() {
    // given
    Rate expected = new Rate();
    expected.setCurrency("USDT");
    expected.setValue(new BigDecimal("1.0000000000"));

    // when
    List<Rate> actual = adapter.getAll();

    // then
    assertNotNull(actual);
    assertEquals(3, actual.size());
    assertEquals(expected.getCurrency(), actual.get(0).getCurrency());
    assertEquals(expected.getValue(), actual.get(0).getValue());
  }

  @Test
  @Sql(scripts = {"classpath:sql/init-sequence.sql", "classpath:sql/insert-rate.sql"})
  void shouldReturnRatesByCurrencyAndDate() {
    // given
    Rate expected = new Rate();
    expected.setCurrency("USDT");
    expected.setValue(new BigDecimal("1.0000000000"));

    // when
    List<Rate> actual = adapter.getByPeriod(expected.getCurrency(), LocalDateTime.now().minusDays(1), LocalDateTime.now());

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected.getCurrency(), actual.get(0).getCurrency());
    assertEquals(expected.getValue(), actual.get(0).getValue());
  }

}
