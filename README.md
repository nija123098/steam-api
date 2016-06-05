Java Steam API
=========

[![Build Status](https://travis-ci.org/go-ive/steam-api.svg?branch=master)](https://travis-ci.org/go-ive/steam-api)&nbsp;
[![Coverage Status](https://coveralls.io/repos/go-ive/steam-api/badge.svg?branch=master)](https://coveralls.io/r/go-ive/steam-api?branch=master)&nbsp;
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.go-ive/steam-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.go-ive/steam-api)

A Java library to retrieve data from Valve's Steam platform. It uses the [Steam Storefront API](https://wiki.teamfortress.com/wiki/User:RJackson/StorefrontAPI), which is not inteded for public use and may change anytime (it already happened). I try to react as quickly as possible when this happens and release a new compatible version, but features might be changed or missing.

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

### Gradle

```gradle
compile 'com.github.go-ive:steam-api:4.1'
```

### Maven

```xml
<dependency>
    <groupId>com.github.go-ive</groupId>
    <artifactId>steam-api</artifactId>
    <version>4.1</version>
</dependency>
```

### Direct Download

Or download the JAR directly from [Maven Central](https://oss.sonatype.org/content/repositories/releases/com/github/go-ive/steam-api/4.1/steam-api-4.1.jar).

## Nightly Build

If you are interested in the version currently in the master branch, you can use the SNAPSHOT version. I do not recommend this for production use as this build is changing. 

```xml
<dependency>
    <groupId>com.github.go-ive</groupId>
    <artifactId>steam-api</artifactId>
    <version>4.2-SNAPSHOT</version>
</dependency>
```
## Change Log

### 4.1
* Added method to retrieve all possible SteamIds

### 4.0
* Introduced SteamId objects and new Retrieval API
* Deprecated old API (retrieveApp with long parameter)

### 3.0.1
* Fixed invalid app id exception for unreleased games

### 3.0
* Major restructuring including test coverage
* API change
* Better appId caching
* Removed InvalidAppIdException - now only SteamApiExceptions are thrown
* Removed a lot of boilerplate/goldplate code

### 2.1.2
* Fixed cast error
* Now validating appIds against the steam API directly

### 2.1.1
* Reworked SteamApp building process
* Added option to specify country code to retrieve prices from other areas

### 2.1.0
* SteamApp object is now immutable
* Merged pull request by KingFaris10

### 2.0
* SteamApi is now an interface with corresponding implementation
* New retrieveData method that takes a list of appIds and returns a list of SteamApps
* SteamApp.getPrice() now returns null if the game is free to play

### 1.1.0
* Changed package declarations to (almost) match group id. Update your import statements.
* SteamApiException is now unchecked.
* Added Slf4J logging.
* Support Information is now parsed and accessible in SteamApp object.
* Added javadoc.
* SteamApiClient is now an interface with a corresponding implementation.