package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import com.pdp.currencyfetcher.api.dto.PollingResponseDto;
import com.pdp.currencyfetcher.mapper.RateMapper;
import com.pdp.currencyfetcher.usecase.PollUpdatedResponseUseCase;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatedResponseRetriever implements PollUpdatedResponseUseCase {

  private final RatePersistenceAdapter ratePersistenceAdapter;
  private final VersionPersistenceAdapter versionPersistenceAdapter;
  private final RateMapper mapper;

  @Override
  public ResponseEntity fetch(Long version, Long timeout) throws InterruptedException {
    if (version < versionPersistenceAdapter.current()) {
      return ResponseEntity.ok(
          PollingResponseDto.builder()
              .version(versionPersistenceAdapter.next())
              .rates(mapper.toDto(ratePersistenceAdapter.findAll()))
              .build()
      );
    } else {
      long start = System.currentTimeMillis();
      while (System.currentTimeMillis() - start < timeout * 1000) {
        TimeUnit.MILLISECONDS.sleep(500);
        if (version < versionPersistenceAdapter.current()) {
          return ResponseEntity.ok(
              PollingResponseDto.builder()
                  .version(versionPersistenceAdapter.next())
                  .rates(mapper.toDto(ratePersistenceAdapter.findAll()))
                  .build()
          );
        }
      }
    }
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body("No new rates available");
  }


}
