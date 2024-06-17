package com.mdelamo.prices.infrastructure.database;

import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.infrastructure.database.entity.PriceEntity;
import com.mdelamo.prices.infrastructure.database.mapper.JpaPriceMapper;
import com.mdelamo.prices.infrastructure.database.repository.PriceJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceJpaRepositoryAdapterTest {

    @InjectMocks
    private PriceJpaRepositoryAdapter adapter;

    @Mock
    private JpaPriceMapper mapper;

    @Mock
    private PriceJpaRepository repository;


    @Test
    void findByCriteria_shouldReturnPrice_whenFound() {

        final var request = mock(FindPriceRequest.class);

        final var brandId = "1";
        final var productId = "2";
        final var date = LocalDateTime.now();
        final var entity = mock(PriceEntity.class);
        final var price = mock(Price.class);
        List<PriceEntity> entityList = List.of(entity);

        when(request.getBrandId()).thenReturn(brandId);
        when(request.getPriceDateTime()).thenReturn(date);
        when(request.getProductId()).thenReturn(productId);
        when(repository.findAllByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(
                Long.parseLong(productId), Long.parseLong(brandId), date, date)).thenReturn(entityList);
        when(mapper.map(entity)).thenReturn(price);

        final var actual = this.adapter.findAllByCriteria(request);

        verify(request).getBrandId();
        verify(request).getPriceDateTime();
        verify(request).getProductId();
        verify(repository).findAllByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(
                Long.parseLong(productId), Long.parseLong(brandId), date, date);
        verify(mapper).map(entity);

        assertFalse(actual.isEmpty());
        assertEquals(entityList.size(), actual.size());
        assertEquals(price, actual.get(0));

    }

    @Test
    void findByCriteria_shouldReturnEmpty_whenNotFound() {

        final var request = mock(FindPriceRequest.class);

        final var brandId = "1";
        final var productId = "2";
        final var date = LocalDateTime.now();

        when(request.getBrandId()).thenReturn(brandId);
        when(request.getPriceDateTime()).thenReturn(date);
        when(request.getProductId()).thenReturn(productId);
        when(repository.findAllByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(
                Long.parseLong(productId), Long.parseLong(brandId), date, date)).thenReturn(List.of());

        final var actual = this.adapter.findAllByCriteria(request);

        verify(request).getBrandId();
        verify(request).getPriceDateTime();
        verify(request).getProductId();
        verify(repository).findAllByProductIdAndBrandIdAndStartDateIsLessThanEqualAndEndDateIsGreaterThanEqual(
                Long.parseLong(productId), Long.parseLong(brandId), date, date);
        verify(mapper, never()).map(any(PriceEntity.class));

        assertTrue(actual.isEmpty());

    }
}