Feature: Testing a REST API
  Users should be able to submit GET and POST requests to a web service,
  represented by WireMock

  Background:
    Given some product prices

  Scenario: Price request 1
    When user requests price for product 35455 of brand 1 at 2020-06-14T10:00:00
    Then price 35.50 is answered and price data matches requested
  Scenario: Price request 2
    When user requests price for product 35455 of brand 1 at 2020-06-14T16:00:00
    Then price 25.45 is answered and price data matches requested
  Scenario: Price request 3
    When user requests price for product 35455 of brand 1 at 2020-06-14T21:00:00
    Then price 35.50 is answered and price data matches requested
  Scenario: Price request 4
    When user requests price for product 35455 of brand 1 at 2020-06-15T10:00:00
    Then price 30.50 is answered and price data matches requested
  Scenario: Price request 5
    When user requests price for product 35455 of brand 1 at 2020-06-16T21:00:00
    Then price 38.95 is answered and price data matches requested

