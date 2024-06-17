package com.mdelamo.prices.infrastructure.database.mapper;

import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.infrastructure.database.entity.PriceEntity;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class JpaPriceMapperTest {

    private final JpaPriceMapper mapper = Mappers.getMapper(JpaPriceMapper.class);

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("when invoke map it shoudl map price properly")
    void itShouldMap_price_properly() {

        // given
        final var entity = this.easyRandom.nextObject(PriceEntity.class);

        final var expected = new Price();
        expected.setPrice(entity.getPrice());
        expected.setId(String.valueOf(entity.getId()));
        expected.setPriceList(entity.getPriceList());
        expected.setCurrency(entity.getCurrency());
        expected.setPriority(entity.getPriority());
        expected.setBrandId(String.valueOf(entity.getBrandId()));
        expected.setEndDate(entity.getEndDate());
        expected.setProductId(String.valueOf(entity.getProductId()));
        expected.setStartDate(entity.getStartDate());

        // when
        final var actual = this.mapper.map(entity);

        // then
        assertEquals(expected, actual);
    }
}