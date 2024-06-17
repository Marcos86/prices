package com.mdelamo.prices.application;

import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.domain.repository.PriceRepository;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceUseCaseImplTest {

    @InjectMocks
    private PriceUseCaseImpl useCase;

    @Mock
    private PriceRepository repository;

    private final EasyRandom easyRandom = new EasyRandom();


    @Test
    @DisplayName("when find price should find properly")
    void findPrice_should_findProperly() {

        // given
        final var price1 = easyRandom.nextObject(Price.class);
        price1.setPrice(new BigDecimal("1"));
        price1.setPriority(2);
        final var price2 = easyRandom.nextObject(Price.class);
        price2.setPrice(new BigDecimal("1"));
        price2.setPriority(1);
        final var request = mock(FindPriceRequest.class);

        when(this.repository.findAllByCriteria(request)).thenReturn(List.of(price1, price2));

        // when
        final var actual = this.useCase.findPrice(request);

        // then
        verify(this.repository).findAllByCriteria(request);

        assertEquals(price1, actual);
    }

    @Test
    @DisplayName("when find price should return null when not found")
    void findPrice_shouldReturnNull_whenNotFound() {

        // given
        final var request = mock(FindPriceRequest.class);

        when(this.repository.findAllByCriteria(request)).thenReturn(List.of());

        // when
        final var actualPrice = this.useCase.findPrice(request);

        // then
        verify(this.repository).findAllByCriteria(request);

        assertNull(actualPrice);
    }

}