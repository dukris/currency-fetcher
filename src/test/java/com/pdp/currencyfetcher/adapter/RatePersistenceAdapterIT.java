package com.pdp.currencyfetcher.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.pdp.currencyfetcher.domain.Rate;
import integration.IntegrationTest;
import integration.PostgresIntegration;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

class RatePersistenceAdapterIT extends IntegrationTest implements PostgresIntegration {

  @Autowired
  private RatePersistenceAdapter adapter;

  @Test
  @Sql("classpath:sql/init-sequence.sql")
  void shouldSaveRate() {
    // given
    Rate expected = new Rate();
    expected.setCurrency("USDT");
    expected.setRate(new BigDecimal("1.0000000000"));

    // when
    List<Rate> actual = adapter.save(List.of(expected));

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected.getCurrency(), actual.get(0).getCurrency());
    assertEquals(expected.getRate(), actual.get(0).getRate());
  }

  @Test
  @Sql(scripts = {"classpath:sql/init-sequence.sql", "classpath:sql/insert-rate.sql"})
  void shouldPollActualRates() {
    // given
    Rate expected = new Rate();
    expected.setCurrency("USDT");
    expected.setRate(new BigDecimal("1.0000000000"));

    // when
    List<Rate> actual = adapter.poll(0L, 0L);

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected.getCurrency(), actual.get(0).getCurrency());
    assertEquals(expected.getRate(), actual.get(0).getRate());
  }

}
