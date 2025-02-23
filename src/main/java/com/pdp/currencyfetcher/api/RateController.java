package com.pdp.currencyfetcher.api;

import com.pdp.currencyfetcher.api.dto.PollingRequestDto;
import com.pdp.currencyfetcher.api.dto.PollingResponseDto;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.mapper.RateMapper;
import com.pdp.currencyfetcher.usecase.GenerateVersionUseCase;
import com.pdp.currencyfetcher.usecase.RetrieveCurrenciesUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
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


    private final RetrieveCurrenciesUseCase retriever;
    private final GenerateVersionUseCase generator;
    private final ExecutorService executor;
    private final RateMapper mapper;

    @GetMapping
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
                    if (request.getVersion() < generator.current()) {
                        List<Rate> rates = retriever.getAll();
                        result.setResult(ResponseEntity.ok(
                            PollingResponseDto.builder()
                                .version(generator.current())
                                .rates(mapper.toDto(rates))
                                .build()
                        ));
                        return;
                    } else {
                        long start = System.currentTimeMillis();
                        while (System.currentTimeMillis() - start < request.getTimeout() * 1000) {
                            TimeUnit.MILLISECONDS.sleep(500);
                            if (request.getVersion() < generator.current()) {
                                List<Rate> rates = retriever.getAll();
                                result.setResult(ResponseEntity.ok(
                                    PollingResponseDto.builder()
                                        .version(generator.current())
                                        .rates(mapper.toDto(rates))
                                        .build()
                                ));
                                return;
                            }
                        }
                    }
                    result.setResult(
                        ResponseEntity.status(HttpStatus.NO_CONTENT)
                            .body("No new rates available")
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
