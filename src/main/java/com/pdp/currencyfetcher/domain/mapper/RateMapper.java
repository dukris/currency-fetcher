package com.pdp.currencyfetcher.domain.mapper;

import com.pdp.currencyfetcher.api.dto.RateDto;
import com.pdp.currencyfetcher.domain.RateEntity;
import com.pdp.currencyfetcher.gateway.BinanceGateway.RateData;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RateMapper {

  @Mapping(target = "currency", source = "symbol")
  @Mapping(target = "rate", source = "price")
  RateEntity toEntity(RateData data);

  List<RateEntity> toEntity(List<RateData> data);

  List<RateDto> toDto(List<RateEntity> data);

}
