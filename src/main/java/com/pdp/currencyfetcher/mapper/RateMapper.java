package com.pdp.currencyfetcher.mapper;

import com.pdp.currencyfetcher.gateway.BinanceGateway.RateData;
import com.pdp.currencyfetcher.api.dto.RateDto;
import com.pdp.currencyfetcher.domain.Rate;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RateMapper {

  @Mapping(target = "currency", source = "symbol")
  @Mapping(target = "rate", source = "price")
  List<Rate> toEntity(List<RateData> data);

  List<RateDto> toDto(List<Rate> data);
}
