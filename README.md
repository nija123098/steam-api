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

import java.util.Map;

public class DemoApp {

    public static void main(String[] args) {
        // Country codes are always 2 letter. Also possible to use the getCountry() method from Locale
        SteamApi steamApi = new SteamApi("US");

        try {
            // Retrieves a list of all possible steam appIds along with name, in case you want to pre-check
            Map<Integer, String> appList = steamApi.listApps();

            // Fetches information about the steam game including pricing
            SteamApp steamApp = steamApi.retrieve(70); // by appId (exact match)
            SteamApp steamApp2 = steamApi.retrieve("Half-Life"); // by name (fuzzy)

            // Use the getters to retrieve data or these convenience methods
            steamApp.isFreeToPlay();
            steamApp.isCheaperThan(15.99); // supports BigDecimal, Double and Integer
            steamApp.isDiscounted();
            steamApp.isDiscountedByAtLeast(20);
            steamApp.isInAnyCategory("Single-player", "Multi-player");
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

## Contribution

Feel free to open an issue or send me a pull request for any problems you discover or features you would like included.