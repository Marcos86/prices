package com.mdelamo.prices.api.rest;

import com.mdelamo.prices.api.rest.controller.request.PriceDto;
import com.mdelamo.prices.api.rest.mapper.RestPriceMapper;
import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.domain.usecase.PriceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class PriceQueryRestController implements PricesApi {

    private final PriceUseCase priceUseCase;

    private final RestPriceMapper restPriceMapper;

    @Override
    public ResponseEntity<PriceDto> getPriceByDateAndProductAndBrand(final LocalDateTime priceDate, final Long productId, final Long brandId) {
        log.info("GET /prices -> price_date: {} - product_id: {} - brand_id: {}", priceDate, productId, brandId);
        final var findPriceRequest = this.restPriceMapper.map(priceDate, productId, brandId);
        final var price = this.priceUseCase.findPrice(findPriceRequest);
        return buildResponseEntity(price);
    }

    private ResponseEntity<PriceDto> buildResponseEntity(final Price price) {
        return Optional.ofNullable(this.restPriceMapper.map(price))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
