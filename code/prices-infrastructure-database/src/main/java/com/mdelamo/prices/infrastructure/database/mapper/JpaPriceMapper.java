package com.mdelamo.prices.infrastructure.database.mapper;

import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.infrastructure.database.entity.PriceEntity;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(
        componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class JpaPriceMapper {

    public abstract Price map(PriceEntity entity);
}
