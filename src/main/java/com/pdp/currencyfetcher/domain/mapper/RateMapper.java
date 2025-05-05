package com.pdp.currencyfetcher.domain.mapper;

import com.pdp.currencyfetcher.api.dto.BinanceRateDto;
import com.pdp.currencyfetcher.api.dto.RateDto;
import com.pdp.currencyfetcher.domain.Rate;
import com.pdp.currencyfetcher.domain.RateEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RateMapper {

    @Mapping(target = "value", source = "price")
    @Mapping(target = "currency", source = "symbol")
    Rate toModel(BinanceRateDto data);

    List<Rate> toModels(List<BinanceRateDto> dtos);

    List<RateDto> toDto(List<Rate> models);

    List<RateEntity> toEntity(List<Rate> models);

    List<Rate> toModel(List<RateEntity> entities);


}
