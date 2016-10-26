package com.github.goive.steamapi.data;

import com.github.goive.steamapi.exceptions.SteamApiException;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SteamAppBuilder {

    private static final Logger logger = Logger.getLogger(SteamAppBuilder.class);

    private static final String MAC = "mac";
    private static final String LINUX = "linux";
    private static final String WINDOWS = "windows";
    private static final String PLATFORMS = "platforms";
    private static final String PUBLISHERS = "publishers";
    private static final String DEVELOPERS = "developers";
    private static final String PRICE_OVERVIEW = "price_overview";
    private static final String CURRENCY = "currency";
    private static final String INITIAL = "initial";
    private static final String FINAL = "final";
    private static final String DISCOUNT_PERCENT = "discount_percent";
    private static final String DATE = "date";
    private static final String RELEASE_DATE = "release_date";
    private static final String DESCRIPTION = "description";
    private static final String CATEGORIES = "categories";
    private static final String SCORE = "score";
    private static final String METACRITIC = "metacritic";
    private static final String EMAIL = "email";
    private static final String URL = "url";
    private static final String SUPPORT_INFO = "support_info";
    private static final String WEBSITE = "website";
    private static final String HEADER_IMAGE = "header_image";
    private static final String SUPPORTED_LANGUAGES = "supported_languages";
    private static final String ABOUT_THE_GAME = "about_the_game";
    private static final String DETAILED_DESCRIPTION = "detailed_description";
    private static final String REQUIRED_AGE = "required_age";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String DATA = "data";
    private static final String UNCHECKED = "unchecked";

    private long appId;
    private String type;
    private String name;
    private int requiredAge;
    private String detailedDescription;
    private String aboutTheGame;
    private List<String> supportedLanguages;
    private String headerImage;
    private String website;
    private Price price;
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

    public SteamAppBuilder withResultMap(Map<Object, Object> resultMap) throws SteamApiException {
        Set<Object> keySet = resultMap.keySet();
        for (Object key : keySet) {
            appId = Long.parseLong((String) key);

            fillFields(resultMap);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    private void fillFields(Map<Object, Object> resultMap) throws SteamApiException {
        Map<Object, Object> innerMap = (Map<Object, Object>) resultMap.get(appId + "");

        if (!(Boolean) innerMap.get("success")) {
            throw new SteamApiException("Invalid appId: " + appId, null);
        }

        Map<Object, Object> dataMap = (Map<Object, Object>) innerMap.get(DATA);

        parseGenericData(dataMap);
        parsePriceData(dataMap);
        parseMarketData(dataMap);
        parsePlatformData(dataMap);
        parseCategoriesData(dataMap);
        parseReleaseDate(dataMap);
        parseMetacriticData(dataMap);
        parseSupportInfo(dataMap);
    }

    private void parseGenericData(Map<Object, Object> dataMap) {
        type = (String) dataMap.get(TYPE);
        name = (String) dataMap.get(NAME);

        try {
            requiredAge = (Integer) dataMap.get(REQUIRED_AGE);
        } catch (ClassCastException e) {
            logger.debug("Could not parse required age for appId " + appId + " as integer. Trying string...", e);
            requiredAge = Integer.valueOf((String) dataMap.get(REQUIRED_AGE));
        }

        detailedDescription = (String) dataMap.get(DETAILED_DESCRIPTION);
        aboutTheGame = (String) dataMap.get(ABOUT_THE_GAME);

        String supportedLanguagesRaw = (String) dataMap.get(SUPPORTED_LANGUAGES);
        if (supportedLanguagesRaw != null) {
            supportedLanguages = Arrays.asList(supportedLanguagesRaw.split("\\s*,\\s*"));
        }

        headerImage = (String) dataMap.get(HEADER_IMAGE);
        website = (String) dataMap.get(WEBSITE);
    }

    @SuppressWarnings(UNCHECKED)
    private void parsePriceData(Map<Object, Object> dataMap) {
        Map<Object, Object> priceOverview = (Map<Object, Object>) dataMap.get(PRICE_OVERVIEW);

        if (priceOverview == null) {
            logger.info("No price data found. Assuming " + name + " is free to play.");
            return;
        }

        price = new Price(Currency.getInstance((String) priceOverview.get(CURRENCY)),
                new BigDecimal(String.valueOf(priceOverview.get(INITIAL))).divide(new BigDecimal(100)),
                new BigDecimal(String.valueOf(priceOverview.get(FINAL))).divide(new BigDecimal(100)),
                (Integer) priceOverview.get(DISCOUNT_PERCENT));
    }

    @SuppressWarnings(UNCHECKED)
    private void parseMarketData(Map<Object, Object> dataMap) {
        developers = (List<String>) dataMap.get(DEVELOPERS);
        publishers = (List<String>) dataMap.get(PUBLISHERS);
    }

    @SuppressWarnings(UNCHECKED)
    private void parsePlatformData(Map<Object, Object> dataMap) {
        Map<Object, Object> platformsMap = (Map<Object, Object>) dataMap.get(PLATFORMS);

        availableForLinux = (Boolean) platformsMap.get(LINUX);
        availableForMac = (Boolean) platformsMap.get(MAC);
        availableForWindows = (Boolean) platformsMap.get(WINDOWS);
    }

    @SuppressWarnings(UNCHECKED)
    private void parseCategoriesData(Map<Object, Object> dataMap) {
        categories = new ArrayList<>();

        List<Object> categoriesMap = (List<Object>) dataMap.get(CATEGORIES);
        if (categoriesMap == null) {
            logger.info("No categories data found for " + appId + " - " + name);
            return;
        }

        for (Object categoryObject : categoriesMap) {
            Map<Object, Object> categoryItemMap = (Map<Object, Object>) categoryObject;

            String description = (String) categoryItemMap.get(DESCRIPTION);

            categories.add(description);
        }
    }

    @SuppressWarnings(UNCHECKED)
    private void parseReleaseDate(Map<Object, Object> dataMap) {
        Map<Object, Object> releaseMap = (Map<Object, Object>) dataMap.get(RELEASE_DATE);
        String dateString = (String) releaseMap.get(DATE);

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.US);
            releaseDate = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            logger.warn("Could not parse release date for appId " + appId, e);
        }
    }

    @SuppressWarnings(UNCHECKED)
    private void parseMetacriticData(Map<Object, Object> dataMap) {
        Map<Object, Object> metacriticMap = (Map<Object, Object>) dataMap.get(METACRITIC);

        if (metacriticMap == null) {
            logger.info("No metacritic data found for " + appId + " - " + name);
            return;
        }

        metacriticScore = (Integer) metacriticMap.get(SCORE);
        metacriticUrl = (String) metacriticMap.get(URL);
    }

    @SuppressWarnings(UNCHECKED)
    private void parseSupportInfo(Map<Object, Object> dataMap) {
        Map<Object, Object> supportInfoMap = (Map<Object, Object>) dataMap.get(SUPPORT_INFO);

        supportUrl = (String) supportInfoMap.get(URL);
        supportEmail = (String) supportInfoMap.get(EMAIL);
    }

    public SteamApp build() {
        return new SteamApp(appId, type, name, requiredAge, detailedDescription, aboutTheGame, supportedLanguages,
                headerImage, website, price, developers, publishers, availableForLinux, availableForWindows,
                availableForMac, categories, releaseDate, metacriticScore, metacriticUrl, supportUrl, supportEmail);
    }
}
