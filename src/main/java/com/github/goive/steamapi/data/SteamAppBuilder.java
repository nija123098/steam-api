package com.github.goive.steamapi.data;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unchecked")
public class SteamAppBuilder {

    private static final Logger logger = Logger.getLogger(SteamAppBuilder.class);

    private static final Locale DEFAULT_LOCALE = Locale.US;

    private String appId;
    private String type;
    private String name;
    private int requiredAge;
    private String detailedDescription;
    private String aboutTheGame;
    private List<String> supportedLanguages;
    private String headerImage;
    private String website;
    private SteamApp.Price price;
    private List<String> developers;
    private List<String> publishers;
    private boolean availableForLinux;
    private boolean availableForWindows;
    private boolean availableForMac;
    private List<String> categories;
    private Date releaseDate;
    private Integer metacriticScore;
    private String metacriticUrl;
    private String supportUrl;
    private String supportEmail;
    private List<String> genres;

    public SteamAppBuilder(Map<Object, Object> resultMap) {
        Set<Object> keySet = resultMap.keySet();
        for (Object key : keySet) {
            appId = (String) key;

            fillFields(resultMap);
        }
    }

    private void fillFields(Map<Object, Object> resultMap) {
        Map<Object, Object> innerMap = (Map<Object, Object>) resultMap.get(appId);
        Map<Object, Object> dataMap = (Map<Object, Object>) innerMap.get("data");

        parseGenericData(dataMap);
        parsePriceData(dataMap);
        parsePlatformData(dataMap);
        parseCategoriesData(dataMap);
        parseReleaseDate(dataMap);
        parseMetacriticData(dataMap);
        parseSupportInfo(dataMap);
        parseGenres(dataMap);
    }

    private void parseGenres(Map<Object, Object> dataMap) {
        genres = new ArrayList<String>();

        List<Object> genresMap = (List<Object>) dataMap.get("genres");
        if (genresMap != null) {
            for (Object genreObject : genresMap) {
                Map<Object, Object> genreItemMap = (Map<Object, Object>) genreObject;
                String description = (String) genreItemMap.get("description");
                genres.add(description);
            }
        }
    }

    private void parseGenericData(Map<Object, Object> dataMap) {
        type = (String) dataMap.get("type");
        name = (String) dataMap.get("name");

        try {
            requiredAge = (Integer) dataMap.get("required_age");
        } catch (ClassCastException e) {
            logger.debug("Could not parse required age for appId " + appId + " as integer. Trying string...", e);
            requiredAge = Integer.valueOf((String) dataMap.get("required_age"));
        }

        detailedDescription = (String) dataMap.get("detailed_description");
        aboutTheGame = (String) dataMap.get("about_the_game");

        String supportedLanguagesRaw = (String) dataMap.get("supported_languages");
        if (supportedLanguagesRaw != null) {
            supportedLanguages = Arrays.asList(supportedLanguagesRaw.split("\\s*,\\s*"));
        }

        headerImage = (String) dataMap.get("header_image");
        website = (String) dataMap.get("website");
        developers = (List<String>) dataMap.get("developers");
        publishers = (List<String>) dataMap.get("publishers");
    }

    private void parsePriceData(Map<Object, Object> dataMap) {
        Map<Object, Object> priceOverview = (Map<Object, Object>) dataMap.get("price_overview");

        if (priceOverview == null) {
            logger.info("No price data found. Assuming " + name + " is free to play.");
            price = SteamApp.Price.FREE;
            return;
        }

        price = new SteamApp.Price(Currency.getInstance((String) priceOverview.get("currency")),
                new BigDecimal(String.valueOf(priceOverview.get("initial"))).divide(new BigDecimal(100), BigDecimal.ROUND_UP),
                new BigDecimal(String.valueOf(priceOverview.get("final"))).divide(new BigDecimal(100), BigDecimal.ROUND_UP),
                (Integer) priceOverview.get("discount_percent"));
    }

    private void parsePlatformData(Map<Object, Object> dataMap) {
        Map<Object, Object> platformsMap = (Map<Object, Object>) dataMap.get("platforms");

        availableForLinux = (Boolean) platformsMap.get("linux");
        availableForMac = (Boolean) platformsMap.get("mac");
        availableForWindows = (Boolean) platformsMap.get("windows");
    }

    private void parseCategoriesData(Map<Object, Object> dataMap) {
        categories = new ArrayList<String>();

        List<Object> categoriesMap = (List<Object>) dataMap.get("categories");
        if (categoriesMap == null) {
            logger.info("No categories data found for " + appId + " - " + name);
            return;
        }

        for (Object categoryObject : categoriesMap) {
            Map<Object, Object> categoryItemMap = (Map<Object, Object>) categoryObject;

            String description = (String) categoryItemMap.get("description");

            categories.add(description);
        }
    }

    private void parseReleaseDate(Map<Object, Object> dataMap) {
        Map<Object, Object> releaseMap = (Map<Object, Object>) dataMap.get("release_date");
        String dateString = (String) releaseMap.get("date");

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, yyyy", DEFAULT_LOCALE);
            releaseDate = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.warn("Could not parse release date for appId " + appId, e);
        }
    }

    private void parseMetacriticData(Map<Object, Object> dataMap) {
        Map<Object, Object> metacriticMap = (Map<Object, Object>) dataMap.get("metacritic");

        if (metacriticMap == null) {
            logger.info("No metacritic data found for " + appId + " - " + name);
            return;
        }

        metacriticScore = (Integer) metacriticMap.get("score");
        metacriticUrl = (String) metacriticMap.get("url");
    }

    private void parseSupportInfo(Map<Object, Object> dataMap) {
        Map<Object, Object> supportInfoMap = (Map<Object, Object>) dataMap.get("support_info");

        supportUrl = (String) supportInfoMap.get("url");
        supportEmail = (String) supportInfoMap.get("email");
    }

    public SteamApp build() {
        return new SteamApp(appId, type, name, requiredAge, detailedDescription, aboutTheGame, supportedLanguages,
                headerImage, website, price, developers, publishers, availableForLinux, availableForWindows,
                availableForMac, categories, releaseDate, metacriticScore, metacriticUrl, supportUrl, supportEmail,
                genres);
    }
}