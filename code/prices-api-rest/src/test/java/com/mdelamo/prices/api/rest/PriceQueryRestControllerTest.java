package com.mdelamo.prices.api.rest;

import com.mdelamo.prices.api.rest.controller.request.PriceDto;
import com.mdelamo.prices.api.rest.mapper.RestPriceMapper;
import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.domain.usecase.PriceUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceQueryRestControllerTest {

    @InjectMocks
    private PriceQueryRestController controller;

    @Mock
    private PriceUseCase useCase;

    @Mock
    private RestPriceMapper mapper;


    @Test
    @DisplayName("when get price by date and product brand should find properly and answer 200")
    void getPriceByDateAndProductAndBrandShouldFindProperlyAndAnswer200() {

        // given
        final LocalDateTime date = LocalDateTime.now();
        final Long brand = 1L;
        final Long product = 2L;
        final Price price = mock(Price.class);
        final PriceDto priceDto = mock(PriceDto.class);
        final var request = mock(FindPriceRequest.class);

        when(mapper.map(date, product, brand)).thenReturn(request);
        when(useCase.findPrice(request)).thenReturn(price);
        when(mapper.map(price)).thenReturn(priceDto);

        // when
        final var actual = controller.getPriceByDateAndProductAndBrand(date, product, brand);

        // then
        verify(mapper).map(date, product, brand);
        verify(mapper).map(price);
        verify(useCase).findPrice(request);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(priceDto, actual.getBody());
    }

    @Test
    @DisplayName("when get price by date and product brand should find properly without results and answer 404")
    void getPriceByDateAndProductAndBrandShouldFindProperlyWithoutResultsAndAnswer404() {

        // given
        final LocalDateTime date = LocalDateTime.now();
        final Long brand = 1L;
        final Long product = 99L;
        final var request = mock(FindPriceRequest.class);

        when(mapper.map(date, product, brand)).thenReturn(request);

        // when
        final var actual = controller.getPriceByDateAndProductAndBrand(date, product, brand);

        // then
        verify(mapper).map(date, product, brand);

        assertNotNull(actual);
        assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
        assertNull(actual.getBody());
    }
}