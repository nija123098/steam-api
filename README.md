Java Steam API
=========

[![Build Status](https://travis-ci.org/go-ive/steam-api.svg?branch=master)](https://travis-ci.org/go-ive/steam-api)&nbsp;
[![Coverage Status](https://coveralls.io/repos/go-ive/steam-api/badge.svg?branch=master)](https://coveralls.io/r/go-ive/steam-api?branch=master)&nbsp;
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.go-ive/steam-api/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.go-ive/steam-api)

A Java library to retrieve data from Valve's Steam platform. It uses the [Steam Storefront API](https://wiki.teamfortress.com/wiki/User:RJackson/StorefrontAPI), which is not inteded for public use and may change anytime (it already happened). I try to react as quickly as possible when this happens and release a new compatible version, but features might be changed or missing.

## Usage

```java
import com.github.goive.steamapi.SteamApi;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class DemoApp {

    public static void main(String[] args) {
        // Country codes are always 2 letter. Also possible to use the getCountry() method from Locale
        SteamApi steamApi = new SteamApi("US");

        try {
            // Retrieves a list of all possible steam Ids, in case you want to pre-check
            List<String> steamIds = steamApi.listIds();

            // Fetches information about the steam game including pricing
            SteamApp steamApp = steamApi.retrieve("70");

            // Use the getters to retrieve data.
            Date releaseDate = steamApp.getReleaseDate();
            List<String> supportedLanguages = steamApp.getSupportedLanguages();
            String name = steamApp.getName();
            BigDecimal finalPrice = steamApp.getPrice().getFinalPrice();
            // ... and so on

            // Convenience methods
            boolean freeToPlay = steamApp.isFreeToPlay();
            boolean discounted = steamApp.isDiscounted();
            boolean discountedByAtLeast20Percent = steamApp.isDiscountedByAtLeast(20);
            boolean inAnyCategory = steamApp.isInAnyCategory("Single-player", "Multi-player");
        } catch (SteamApiException e) {
            // Exception needs to be thrown here in case of invalid appId or service downtime
            e.printStackTrace();
        }
    }

}
```

This retrieves almost all available data for the given Steam ID including prices and discounts.

## Releases

### Gradle

```gradle
compile 'com.github.go-ive:steam-api:5.0'
```

### Maven

```xml
<dependency>
    <groupId>com.github.go-ive</groupId>
    <artifactId>steam-api</artifactId>
    <version>5.0</version>
</dependency>
```

### Direct Download

Or download the JAR directly from [Maven Central](https://oss.sonatype.org/content/repositories/releases/com/github/go-ive/steam-api/5.0/steam-api-5.0.jar).

## Change Log

### 5.0
* Major API overhaul and slimming down of code.

### 4.3
* Ensured compatibility with Java 7 for Android Projects. (Based on Issue #12)

### 4.2
* Merged pull request #11: Type "DLC" is now available in SteamApp.

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
