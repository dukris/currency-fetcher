package com.pdp.currencyfetcher.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.domain.RateEntity;
import com.pdp.currencyfetcher.extensions.FakeRate;
import com.pdp.currencyfetcher.adapter.repository.RateRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RatePersistenceAdapterImplTest {

  @Mock
  private RateRepository repository;
  @Mock
  private VersionPersistenceAdapter versionPersistenceAdapter;
  @InjectMocks
  private RatePersistenceAdapterImpl adapter;

  @Test
  @ExtendWith(FakeRate.class)
  void shouldSaveCurrenciesAndRates(RateEntity rate) {
    // given
    List<RateEntity> expected = List.of(rate);
    when(repository.saveAll(any())).thenReturn(expected);

    // when
    List<RateEntity> actual = adapter.save(expected);

    // then
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());
    assertEquals(expected, actual);
    verify(repository).saveAll(any());
    verify(versionPersistenceAdapter).next();
  }

  @Test
  @ExtendWith(FakeRate.class)
  void shouldRetrieveAllCurrenciesAndRates(RateEntity rate) {
    // given
    List<RateEntity> expected = List.of(rate);
    when(repository.findAll()).thenReturn(expected);

    // when
    List<RateEntity> actual = adapter.findAll();

    // then
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());
    assertEquals(expected, actual);
    verify(repository).findAll();
  }

}
