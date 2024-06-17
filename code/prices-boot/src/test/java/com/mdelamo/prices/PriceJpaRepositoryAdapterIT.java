package com.mdelamo.prices;

import com.mdelamo.prices.domain.model.FindPriceRequest;
import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.infrastructure.database.PriceJpaRepositoryAdapter;
import com.mdelamo.prices.infrastructure.database.entity.PriceEntity;
import com.mdelamo.prices.infrastructure.database.repository.PriceJpaRepository;
import com.mdelamo.prices.util.BootPriceMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class PriceJpaRepositoryAdapterIT {

    @Autowired
    private PriceJpaRepositoryAdapter priceJpaRepositoryAdapter;

    @Autowired
    private PriceJpaRepository priceJpaRepository;

    @Autowired
    private BootPriceMapper bootPriceMapper;


    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("when find price by date and product and brand should find active price")
    void findPriceByDateAndProductAndBrandShouldFindActivePrices() {

        // given
        final var brandId = "1";
        final var date = LocalDateTime.now();
        final var productId = "1";
        final var priceId = "10";
        final var priceId2 = "11";
        final var priceId3 = "12";
        final var priceId4 = "13";

        final var request = new FindPriceRequest();
        request.setProductId(productId);
        request.setBrandId(brandId);
        request.setPriceDateTime(date);

        final var yesterdaysPrice = Price.builder()
                                      .startDate(date.minusDays(2L))
                                      .endDate(date.minusDays(1L))
                                      .productId(productId)
                                      .brandId(brandId)
                                      .priceList(1L)
                                      .priority(0)
                                      .price(BigDecimal.TEN)
                                      .currency("EUR")
                                      .id(priceId3)
                                      .build();
        final var todaysPrice = Price.builder()
                                           .startDate(date.minusDays(1L))
                                           .endDate(date.plusDays(1L))
                                           .productId(productId)
                                           .brandId(brandId)
                                           .priority(1)
                                           .priceList(2L)
                                           .price(BigDecimal.TEN)
                                           .currency("EUR")
                                           .id(priceId)
                                           .build();
        final var todaysPrice2 = Price.builder()
                                           .startDate(date.minusDays(1L))
                                           .endDate(date.plusDays(1L))
                                           .productId(productId)
                                           .brandId(brandId)
                                           .priority(2)
                                           .priceList(3L)
                                           .price(BigDecimal.TEN)
                                           .currency("EUR")
                                           .id(priceId4)
                                           .build();
        final var tomorrowsPrice = Price.builder()
                                              .startDate(date.plusDays(1L))
                                              .endDate(date.plusDays(2L))
                                              .productId(productId)
                                              .brandId(brandId)
                                              .priority(2)
                                              .priceList(4L)
                                              .price(BigDecimal.TEN)
                                              .currency("EUR")
                                              .id(priceId2)
                                              .build();


        persistEntity(bootPriceMapper.map(yesterdaysPrice));
        persistEntity(bootPriceMapper.map(todaysPrice));
        persistEntity(bootPriceMapper.map(todaysPrice2));
        persistEntity(bootPriceMapper.map(tomorrowsPrice));

        // when
        final var actual = priceJpaRepositoryAdapter.findAllByCriteria(request);

        // then
        assertFalse(actual.isEmpty());
        assertEquals(2, actual.size());
        assertThat(actual).contains(todaysPrice);
        assertThat(actual).contains(todaysPrice2);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("when find price by date and product and brand should return empty when not found")
    void findPriceByDateAndProductAndBrandShouldReturnEmptyWhenNotFound() {

        // given
        final var brandId = "1";
        final var date = LocalDateTime.now();
        final var productId = "1";
        final var priceId2 = "11";
        final var priceId3 = "12";

        final var request = new FindPriceRequest();
        request.setProductId(productId);
        request.setBrandId(brandId);
        request.setPriceDateTime(date);


        final var yesterdaysPrice = Price.builder()
                                               .startDate(date.minusDays(2L))
                                               .endDate(date.minusDays(1L))
                                               .productId(productId)
                                               .brandId(brandId)
                                               .priority(0)
                                               .priceList(1L)
                                               .price(BigDecimal.TEN)
                                               .currency("EUR")
                                               .id(priceId3)
                                               .build();
        final var tomorrowsPrice = Price.builder()
                                              .startDate(date.plusDays(1L))
                                              .endDate(date.plusDays(2L))
                                              .productId(productId)
                                              .brandId(brandId)
                                              .priority(2)
                                              .priceList(2L)
                                              .price(BigDecimal.TEN)
                                              .currency("EUR")
                                              .id(priceId2)
                                              .build();

        persistEntity(bootPriceMapper.map(yesterdaysPrice));
        persistEntity(bootPriceMapper.map(tomorrowsPrice));

        // when
        final var actual = priceJpaRepositoryAdapter.findAllByCriteria(request);

        // when
        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }

    private void persistEntity(final PriceEntity entity) {
        priceJpaRepository.save(entity);
        priceJpaRepository.flush();
    }

}
