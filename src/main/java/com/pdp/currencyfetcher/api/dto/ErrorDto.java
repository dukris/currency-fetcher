package com.pdp.currencyfetcher.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

  @Schema(name = "Error message")
  private String message;

  @Schema(name = "Date and time")
  private LocalDateTime timestamp;

  public ErrorDto(String message) {
    this(message, LocalDateTime.now());
  }

}
