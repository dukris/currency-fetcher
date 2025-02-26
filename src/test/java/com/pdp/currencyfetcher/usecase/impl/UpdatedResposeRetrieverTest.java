package com.pdp.currencyfetcher.usecase.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import com.pdp.currencyfetcher.api.dto.PollingResponseDto;
import com.pdp.currencyfetcher.api.dto.RateDto;
import com.pdp.currencyfetcher.domain.RateEntity;
import com.pdp.currencyfetcher.extensions.FakeRate;
import com.pdp.currencyfetcher.extensions.FakeRateDto;
import com.pdp.currencyfetcher.mapper.RateMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class UpdatedResposeRetrieverTest {

  private final Long nextVersion = 2L;
  private final Long currentVersion = 1L;

  @Mock
  private RatePersistenceAdapter ratePersistenceAdapter;
  @Mock
  private VersionPersistenceAdapter versionPersistenceAdapter;
  @Mock
  private RateMapper mapper;
  @InjectMocks
  private UpdatedResponseRetriever fetcher;

  @Test
  @ExtendWith({FakeRate.class, FakeRateDto.class})
  void shouldFetchUpdatedRatesIfProvidedVersionIsLower(RateEntity rate, RateDto dto)
      throws InterruptedException {
    // given
    ResponseEntity expected = ResponseEntity.ok(
        PollingResponseDto.builder()
            .version(nextVersion)
            .rates(List.of(dto))
            .build()
    );
    when(versionPersistenceAdapter.current()).thenReturn(currentVersion);
    when(versionPersistenceAdapter.next()).thenReturn(nextVersion);
    when(ratePersistenceAdapter.findAll()).thenReturn(List.of(rate));
    when(mapper.toDto(List.of(rate))).thenReturn(List.of(dto));

    // when
    ResponseEntity actual = fetcher.fetch(currentVersion - 1, 1L);

    // then
    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(versionPersistenceAdapter).current();
    verify(versionPersistenceAdapter).next();
    verify(ratePersistenceAdapter).findAll();
  }

  @Test
  @ExtendWith({FakeRate.class, FakeRateDto.class})
  void shouldFetchUpdatedRatesAfterTimeoutIfProvidedVersionIsHigher(RateEntity rate, RateDto dto)
      throws InterruptedException {
    // given
    ResponseEntity expected = ResponseEntity.ok(
        PollingResponseDto.builder()
            .version(nextVersion)
            .rates(List.of(dto))
            .build()
    );
    when(versionPersistenceAdapter.current()).thenReturn(currentVersion, currentVersion + 1);
    when(versionPersistenceAdapter.next()).thenReturn(nextVersion);
    when(ratePersistenceAdapter.findAll()).thenReturn(List.of(rate));
    when(mapper.toDto(List.of(rate))).thenReturn(List.of(dto));

    // when
    ResponseEntity actual = fetcher.fetch(currentVersion, 5L);

    // then
    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(versionPersistenceAdapter, times(2)).current();
    verify(versionPersistenceAdapter).next();
    verify(ratePersistenceAdapter).findAll();
  }

  @Test
  void shouldReturnEmptyResponseIfProvidedVersionIsHigher() throws InterruptedException {
    // given
    ResponseEntity expected = ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body("No new rates available");
    when(versionPersistenceAdapter.current()).thenReturn(currentVersion);

    // when
    ResponseEntity actual = fetcher.fetch(currentVersion + 1, 1L);

    // then
    assertNotNull(actual);
    assertEquals(expected, actual);
    verify(versionPersistenceAdapter, times(3)).current();
  }

}
