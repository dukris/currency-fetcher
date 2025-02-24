package com.pdp.currencyfetcher.api;

import com.pdp.currencyfetcher.api.dto.PollingRequestDto;
import com.pdp.currencyfetcher.usecase.PollUpdatedResponseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rates")
@Tag(name = "Controller for rates")
public class RateController {

  private final PollUpdatedResponseUseCase fetcher;
  private final ExecutorService executor;

  @GetMapping
  @Operation(summary = "Get all actual rates")
  public DeferredResult<ResponseEntity> getRates(
      @RequestBody PollingRequestDto request
  ) {
    DeferredResult<ResponseEntity> result = new DeferredResult<>(
        request.getTimeout(),
        ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
            .body("Request timeout")
    );
    CompletableFuture.runAsync(() -> {
          try {
            result.setResult(
                fetcher.fetch(request.getVersion(), request.getTimeout())
            );
          } catch (Exception ex) {
            log.error("Error while fetching rates", ex);
            result.setErrorResult(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error")
            );
          }
        }, executor
    );
    return result;
  }

}
