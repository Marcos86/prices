package com.mdelamo.prices.api.rest.mapper;

import com.mdelamo.prices.api.rest.controller.request.PriceDto;
import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

import java.time.LocalDateTime;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class RestPriceMapper {

    public abstract FindPriceRequest map(LocalDateTime priceDateTime, Long productId, Long brandId);

    public abstract PriceDto map(Price price);
}
