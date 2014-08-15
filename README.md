Java Steam API
=========

[![Build Status](https://drone.io/github.com/go-ive/steam-api/status.png)](https://drone.io/github.com/go-ive/steam-api/latest)

A Java library to retrieve data from Valves Steam platform.

You can use it like in this demo application:

```java
package com.github.goive.steamapidemo;

import java.util.List;

import com.github.goive.steamapi.SteamApi;
import com.github.goive.steamapi.SteamApiImpl;
import com.github.goive.steamapi.data.Category;
import com.github.goive.steamapi.data.Price;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.enums.CountryCode;

public class DemoApp {

    public static void main(String[] args) {
        SteamApi steamApi = new SteamApiImpl();
        steamApi.setCountryCode(CountryCode.US);

        Long appIdOfHalfLife = 70L;

        System.out.println("Retrieving single game for id " + appIdOfHalfLife + "...");

        SteamApp steamApp = steamApi.retrieveData(appIdOfHalfLife);
        String name = steamApp.getName();

        if (steamApp.isFreeToPlay()) {
            System.out.println(name + " is free to play!");
        } else {
            Price price = steamApp.getPrice();

            StringBuilder sb = new StringBuilder();
            sb.append(name);
            sb.append(" is currently at ");
            sb.append(price.getCurrency());
            sb.append(" ");
            sb.append(price.getFinalPrice());
            sb.append(" with a ");
            sb.append(price.getDiscountPercent());
            sb.append("% discount from ");
            sb.append(price.getCurrency());
            sb.append(" ");
            sb.append(price.getInitialPrice());

            System.out.println(sb.toString());
        }

        System.out.println(name + " is listed in these categories:");
        List<Category> categories = steamApp.getCategories();
        categories.stream().forEach((category) -> {
            System.out.println(" - " + category.getDescription());
        });

        System.out.println("The game is available in the following languages: "
            + steamApp.getSupportedLanguages().toString());

        System.out.println(name + " was released at " + steamApp.getReleaseDate());

        System.out.println("Here's some more info about the game:\n" + steamApp.getAboutTheGame());

        System.out.println(name + " is available for the following platforms: ");
        if (steamApp.isAvailableForLinux()) {
            System.out.println(" - Linux");
        }
        if (steamApp.isAvailableForWindows()) {
            System.out.println(" - Windows");
        }
        if (steamApp.isAvailableForMac()) {
            System.out.println(" - Mac");
        }

        System.out.println("For more functionality take a look at the javadoc of the SteamApp.class.");
    }
}
```

This retrieves almost all available data for the given Steam ID or list of IDs, including prices and discounts.

Maven dependency:

```xml
<dependency>
    <groupId>com.github.go-ive</groupId>
    <artifactId>steam-api</artifactId>
    <version>2.1.1</version>
</dependency>
```
