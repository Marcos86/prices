package com.mdelamo.prices.steps;


import com.mdelamo.prices.Application;
import com.mdelamo.prices.api.rest.controller.request.PriceDto;
import com.mdelamo.prices.domain.model.Price;
import com.mdelamo.prices.infrastructure.database.entity.PriceEntity;
import com.mdelamo.prices.infrastructure.database.repository.PriceJpaRepository;
import com.mdelamo.prices.util.BootPriceMapper;
import com.mdelamo.prices.util.DbUtils;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
@CucumberContextConfiguration
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ComponentScan(basePackageClasses = {Application.class})
@EntityScan(basePackageClasses = {PriceEntity.class})
public class PricesSteps {

    private static final String URL_PREFIX = "http://localhost:";
    private static final String CUSTOMERS = "/prices";

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DbUtils dbUtils;

    @Autowired
    private BootPriceMapper bootPriceMapper;

    @Autowired
    private PriceJpaRepository priceJpaRepository;

    private ResponseEntity<PriceDto> response;

    private Long productIdRequest;

    private Long brandIdRequest;

    private LocalDateTime dateTimeRequest;

    @After
    public void cleanDB() {
        dbUtils.cleanDb();
    }

    @Given("^some product prices$")
    public void givenAnUserAndSomeProductPrices() {
        loadDbData();
    }

    @When("^user requests price for product (.+) of brand (.+) at (.+)$")
    public void userRequestsPriceForProductOfBrandAt(final long productId, final long brandId, final String dateTime) {
        final var priceDateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        final var offsetPriceDateText = priceDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.productIdRequest = productId;
        this.brandIdRequest = brandId;
        this.dateTimeRequest = priceDateTime;

        response =
            testRestTemplate.getForEntity(
                URL_PREFIX + port + CUSTOMERS + "?"
                    + "product_id=" + productId
                    + "&" + "brand_id=" + brandId
                    + "&" + "price_date=" + offsetPriceDateText,
                PriceDto.class);
    }

    @Then("^price (.+) is answered and price data matches requested")
    public void priceWithCurrencyEURIsAnswered(final String price) {
        assertNotNull(response.getBody());

        final var priceDto = response.getBody();
        final var expectedAmount = new BigDecimal(price);
        final var startDate = priceDto.getStartDate();
        final var endDate = priceDto.getEndDate();

        assertEquals(expectedAmount, priceDto.getPrice());
        assertFalse(dateTimeRequest.isBefore(startDate));
        assertFalse(dateTimeRequest.isAfter(endDate));
        assertEquals(priceDto.getBrandId(), brandIdRequest);
        assertEquals(priceDto.getProductId(), productIdRequest);
    }

    private void loadDbData() {

        final var priceBuilder = Price.builder()
                                            .brandId("1")
                                            .currency("EUR")
                                            .productId("35455");

        final var priceEntity1 = priceBuilder
            .id("1")
            .price(new BigDecimal("35.50"))
            .priority(0)
            .priceList(1L)
            .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
            .build();
        savePrice(priceEntity1);
        final var priceEntity2 = priceBuilder
            .id("2")
            .price(new BigDecimal("25.45"))
            .priority(1)
            .priceList(2L)
            .startDate(LocalDateTime.of(2020, Month.JUNE, 14, 15, 0, 0))
            .endDate(LocalDateTime.of(2020, Month.JUNE, 14, 18, 30, 0))
            .build();
        savePrice(priceEntity2);
        final var priceEntity3 = priceBuilder
            .id("3")
            .price(new BigDecimal("30.50"))
            .priority(1)
            .priceList(3L)
            .startDate(LocalDateTime.of(2020, Month.JUNE, 15, 0, 0, 0))
            .endDate(LocalDateTime.of(2020, Month.JUNE, 15, 11, 0, 0))
            .build();
        savePrice(priceEntity3);
        final var priceEntity4 = priceBuilder
            .id("4")
            .price(new BigDecimal("38.95"))
            .priority(1)
            .priceList(4L)
            .startDate(LocalDateTime.of(2020, Month.JUNE, 15, 16, 0, 0))
            .endDate(LocalDateTime.of(2020, Month.DECEMBER, 31, 23, 59, 59))
            .build();
        savePrice(priceEntity4);
    }

    private void savePrice(final Price price) {
        priceJpaRepository.save(bootPriceMapper.map(price));
        priceJpaRepository.flush();
    }
}
