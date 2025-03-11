package com.pdp.currencyfetcher.domain.mapper;

import com.pdp.currencyfetcher.api.dto.BinanceRateDto;
import com.pdp.currencyfetcher.api.dto.RateDto;
import com.pdp.currencyfetcher.domain.RateEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RateMapper {

  @Mapping(target = "value", source = "price")
  @Mapping(target = "currency", source = "symbol")
  RateEntity toEntity(BinanceRateDto data);

  List<RateEntity> toEntity(List<BinanceRateDto> data);

  List<RateDto> toDto(List<RateEntity> data);

}
