Java Steam API
=========

[![Build Status](https://travis-ci.org/go-ive/steam-api.svg?branch=master)](https://travis-ci.org/go-ive/steam-api)&nbsp;
[![Coverage Status](https://coveralls.io/repos/go-ive/steam-api/badge.svg?branch=master)](https://coveralls.io/r/go-ive/steam-api?branch=master)&nbsp;
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.go-ive/steam-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.go-ive/steam-api)

A Java library to retrieve data from Valves Steam platform. It uses the [Steam Storefront API](https://wiki.teamfortress.com/wiki/User:RJackson/StorefrontAPI), which is not inteded for public use and may change anytime (it already happened). I try to react as quickly as possible when this happens and release a new compatible version, but features might be changed or missing.

## Usage

```java
package com.github.goive.steamapidemo;

import com.github.goive.steamapi.SteamApi;
import com.github.goive.steamapi.SteamApiFactory;
import com.github.goive.steamapi.data.Price;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamId;
import com.github.goive.steamapi.enums.CountryCode;

import java.math.BigDecimal;
import java.util.Currency;

public class DemoApp {

    public static void main(String[] args) {
        SteamApi steamApi = SteamApiFactory.createSteamApi(CountryCode.AT);

        SteamId steamIdOfHalfLife = SteamId.create("70");

        SteamApp steamApp = steamApi.retrieveApp(steamIdOfHalfLife);

        Price price = steamApp.getPrice();

        BigDecimal currentPriceOfApp = price.getFinalPrice();
        BigDecimal priceBeforeDiscount = price.getInitialPrice();
        int discountPercentage = price.getDiscountPercent();
        Currency currency = price.getCurrency();

        // other info also available in steamApp object...
    }

}
```

This retrieves almost all available data for the given Steam ID including prices and discounts.

## Stable Release

### Maven

```xml
<dependency>
    <groupId>com.github.go-ive</groupId>
    <artifactId>steam-api</artifactId>
    <version>4.0</version>
</dependency>
```

### Direct Download

Or download the JAR directly from [Maven Central](https://oss.sonatype.org/content/repositories/releases/com/github/go-ive/steam-api/4.0/steam-api-4.0.jar).

## Nightly Build

If you are interested in the version currently in the master branch, you can use the SNAPSHOT version. I do not recommend this for production use as this build is changing. 

```xml
<dependency>
    <groupId>com.github.go-ive</groupId>
    <artifactId>steam-api</artifactId>
    <version>4.1-SNAPSHOT</version>
</dependency>
```
