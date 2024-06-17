package com.mdelamo.prices.api.rest.mapper;

import com.mdelamo.prices.api.rest.controller.request.PriceDto;
import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RestPriceMapperTest {

    @InjectMocks
    private RestPriceMapper mapper = Mappers.getMapper(RestPriceMapper.class);

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    void itShouldMap_PriceDto_properly() {

        final var domain = this.easyRandom.nextObject(Price.class);
        domain.setBrandId("1");
        domain.setProductId("2");
        final var actualDto = this.mapper.map(domain);

        final var expectedDto = new PriceDto();

        expectedDto.setPrice(domain.getPrice());
        expectedDto.setBrandId(Long.parseLong(domain.getBrandId()));
        expectedDto.setEndDate(domain.getEndDate());
        expectedDto.setStartDate(domain.getStartDate());
        expectedDto.setPriceList(domain.getPriceList());
        expectedDto.setProductId(Long.parseLong(domain.getProductId()));

        assertEquals(expectedDto, actualDto);

    }

    @Test
    void itShouldMap_FindPriceRequest_properly() {

        final var dateTime = LocalDateTime.now();
        final var productId = 1L;
        final var brandId = 2L;

        final var expected = new FindPriceRequest();
        expected.setPriceDateTime(dateTime);
        expected.setProductId(String.valueOf(productId));
        expected.setBrandId(String.valueOf(brandId));

        final var actual = this.mapper.map(dateTime, productId, brandId);

        assertEquals(expected, actual);
    }

}
