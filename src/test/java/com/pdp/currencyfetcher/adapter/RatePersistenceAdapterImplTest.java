package com.pdp.currencyfetcher.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.adapter.repository.RateRepository;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.exception.NoUpdatedContentException;
import com.pdp.currencyfetcher.extensions.FakeRate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RatePersistenceAdapterImplTest {

  private final Long currentVersion = 1L;

  @Mock
  private RateRepository repository;
  @Mock
  private VersionPersistenceAdapter versionPersistenceAdapter;
  @InjectMocks
  private RatePersistenceAdapterImpl adapter;

  @Test
  @ExtendWith(FakeRate.class)
  void shouldSaveCurrenciesAndRates(Rate rate) {
    // given
    List<Rate> expected = List.of(rate);
    when(repository.saveAll(any())).thenReturn(expected);

    // when
    List<Rate> actual = adapter.save(expected);

    // then
    assertNotNull(actual);
    assertEquals(expected.size(), actual.size());
    assertEquals(expected, actual);
    verify(repository).saveAll(any());
    verify(versionPersistenceAdapter).next();
  }

  @Test
  @ExtendWith(FakeRate.class)
  void shouldPollRatesIfProvidedVersionIsLower(Rate expected) {
    // given
    when(versionPersistenceAdapter.current()).thenReturn(currentVersion);
    when(repository.findAll()).thenReturn(List.of(expected));

    // when
    List<Rate> actual = adapter.poll(currentVersion - 1, 1L);

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected, actual.get(0));
    verify(versionPersistenceAdapter).current();
    verify(repository).findAll();
  }

  @Test
  @ExtendWith(FakeRate.class)
  void shouldPollUpdatedRatesAfterTimeoutIfProvidedVersionIsHigher(Rate expected) {
    // given
    when(versionPersistenceAdapter.current()).thenReturn(currentVersion, currentVersion + 1);
    when(repository.findAll()).thenReturn(List.of(expected));

    // when
    List<Rate> actual = adapter.poll(currentVersion, 5L);

    // then
    assertNotNull(actual);
    assertEquals(1, actual.size());
    assertEquals(expected, actual.get(0));
    verify(versionPersistenceAdapter, times(2)).current();
    verify(repository).findAll();
  }

  @Test
  void shouldThrowNoContentExceptionIfProvidedVersionIsHigher() {
    // given
    when(versionPersistenceAdapter.current()).thenReturn(currentVersion);

    // when
    Executable executable = () -> adapter.poll(currentVersion + 1, 1L);

    // then
    assertThrows(NoUpdatedContentException.class, executable);
  }

}
