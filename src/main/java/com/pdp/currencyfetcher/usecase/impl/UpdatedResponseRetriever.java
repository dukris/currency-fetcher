package com.pdp.currencyfetcher.usecase.impl;

import com.pdp.currencyfetcher.api.dto.PollingResponseDto;
import com.pdp.currencyfetcher.mapper.RateMapper;
import com.pdp.currencyfetcher.usecase.PollUpdatedResponseUseCase;
import com.pdp.currencyfetcher.usecase.GenerateVersionUseCase;
import com.pdp.currencyfetcher.usecase.RetrieveCurrenciesUseCase;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdatedResponseRetriever implements PollUpdatedResponseUseCase {

  private final RetrieveCurrenciesUseCase retriever;
  private final GenerateVersionUseCase generator;
  private final RateMapper mapper;

  @Override
  public ResponseEntity fetch(Long version, Long timeout) throws InterruptedException {
    if (version < generator.current()) {
      return ResponseEntity.ok(
          PollingResponseDto.builder()
              .version(generator.next())
              .rates(mapper.toDto(retriever.getAll()))
              .build()
      );
    } else {
      long start = System.currentTimeMillis();
      while (System.currentTimeMillis() - start < timeout * 1000) {
        TimeUnit.MILLISECONDS.sleep(500);
        if (version < generator.current()) {
          return ResponseEntity.ok(
              PollingResponseDto.builder()
                  .version(generator.next())
                  .rates(mapper.toDto(retriever.getAll()))
                  .build()
          );
        }
      }
    }
    return ResponseEntity.status(HttpStatus.NO_CONTENT)
        .body("No new rates available");
  }


}
