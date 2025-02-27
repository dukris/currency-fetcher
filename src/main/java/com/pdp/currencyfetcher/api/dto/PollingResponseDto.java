package com.pdp.currencyfetcher.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PollingResponseDto {

    @Schema(name = "Unique identifier of the rate")
    private Long version;

    @Schema(name = "List of currencies and rates")
    List<RateDto> rates;

}
