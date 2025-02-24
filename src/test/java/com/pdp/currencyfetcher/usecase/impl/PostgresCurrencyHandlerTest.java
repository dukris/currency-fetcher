package com.pdp.currencyfetcher.usecase.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.repository.RateRepository;
import com.pdp.currencyfetcher.usecase.GenerateVersionUseCase;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PostgresCurrencyHandlerTest {

  private final List<Rate> expected = List.of(new Rate(UUID.randomUUID(), "USDT", new BigDecimal(1), LocalDateTime.now()));

  @Mock
  private RateRepository repository;
  @Mock
  private GenerateVersionUseCase generator;
  @InjectMocks
  private PostgresCurrenciesHandler handler;

  @Test
  void shouldSaveCurrenciesAndRates() {
    // given
    when(repository.saveAll(any())).thenReturn(expected);

    // when
    List<Rate> actual = handler.save(expected);

    // then
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());
    assertEquals(expected, actual);
    verify(repository).saveAll(any());
    verify(generator).next();
  }

  @Test
  void shouldRetrieveAllCurrenciesAndRates() {
    // given
    when(repository.findAll()).thenReturn(expected);

    // when
    List<Rate> actual = handler.getAll();

    // then
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());
    assertEquals(expected, actual);
    verify(repository).findAll();
  }

}
