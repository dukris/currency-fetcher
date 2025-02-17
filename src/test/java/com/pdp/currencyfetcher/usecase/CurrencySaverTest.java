package com.pdp.currencyfetcher.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.repository.RateRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CurrencySaverTest {

  @Mock
  private RateRepository repository;

  @InjectMocks
  private CurrencySaver saver;

  @Test
  void shouldSaveCurrenciesAndRates() {
    // given
    Rate expected = new Rate(UUID.randomUUID(), "USDT", new BigDecimal(1), LocalDateTime.now());
    when(repository.save(any())).thenReturn(expected);

    // when
    Rate actual = saver.save(expected);

    // then
    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(repository).save(any());
  }

}
