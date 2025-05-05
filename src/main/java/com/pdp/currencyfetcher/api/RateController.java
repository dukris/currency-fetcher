package com.pdp.currencyfetcher.api;

import com.pdp.currencyfetcher.adapter.RatePersistenceAdapter;
import com.pdp.currencyfetcher.adapter.VersionPersistenceAdapter;
import com.pdp.currencyfetcher.api.dto.ErrorDto;
import com.pdp.currencyfetcher.api.dto.PollingResponseDto;
import com.pdp.currencyfetcher.api.dto.RateDto;
import com.pdp.currencyfetcher.domain.mapper.RateMapper;
import com.pdp.currencyfetcher.exception.NoUpdatedContentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rates")
@Tag(name = "Controller for rates")
public class RateController {

  private final VersionPersistenceAdapter versionPersistenceAdapter;
  private final RatePersistenceAdapter ratePersistenceAdapter;
  private final ExecutorService executor;
  private final RateMapper mapper;

  @GetMapping
  @Operation(summary = "Get all actual rates immediately")
  public List<RateDto> retrieveAll() {
    return mapper.toDto(ratePersistenceAdapter.getAll());
  }

  @GetMapping("/poll")
  @Operation(summary = "Poll all actual rates according to the version")
  public DeferredResult<ResponseEntity> poll(
      @RequestParam @Min(0) Long version,
      @RequestParam @Min(0) Long timeout
  ) {
    DeferredResult<ResponseEntity> result = new DeferredResult<>();
    CompletableFuture.runAsync(() -> {
          try {
            result.setResult(
                ResponseEntity.ok(
                    PollingResponseDto.builder()
                        .version(versionPersistenceAdapter.next())
                        .rates(mapper.toDto(ratePersistenceAdapter.poll(version, timeout)))
                        .build()));
          } catch (NoUpdatedContentException ex) {
            result.setResult(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
          } catch (Exception ex) {
            log.error("Error while fetching rates", ex);
            result.setErrorResult(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorDto("Error while fetching rates"))
            );
          }
        }, executor
    );
    return result;
  }

}
