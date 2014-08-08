package com.github.goive.steamapi.data;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.github.goive.steamapi.enums.Type;
import com.github.goive.steamapi.exceptions.InvalidAppIdException;

public class SteamAppSingleBuilder {

    private static Logger logger = Logger.getLogger(SteamAppSingleBuilder.class);

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
    private static final String ID = "id";
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
    private Type type;
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
    private List<Category> categories;
    private Date releaseDate;
    private Integer metacriticScore;
    private String metacriticUrl;
    private SupportInfo supportInfo;

    public SteamAppSingleBuilder withResultMap(Map<Object, Object> resultMap) {
        Set<Object> keySet = resultMap.keySet();
        for (Object key : keySet) {
            appId = Long.parseLong((String)key);

            fillFields(resultMap);
        }

        return this;
    }

    @SuppressWarnings("unchecked")
    private void fillFields(Map<Object, Object> resultMap) {
        Map<Object, Object> innerMap = (Map<Object, Object>)resultMap.get(appId + "");

        if (!(Boolean)innerMap.get("success")) {
            throw new InvalidAppIdException(appId + "");
        }

        Map<Object, Object> dataMap = (Map<Object, Object>)innerMap.get(DATA);

        parseGenericData(dataMap);
        parsePriceData(dataMap);
        parseMarketData(dataMap);
        parsePlatformData(dataMap);
        parseCategorieData(dataMap);
        parseReleaseData(dataMap);
        parseMetacriticData(dataMap);
        parseSupportInfo(dataMap);
    }

    private void parseGenericData(Map<Object, Object> dataMap) {
        type = assignType((String)dataMap.get(TYPE));
        name = (String)dataMap.get(NAME);

        try {
            requiredAge = (Integer)dataMap.get(REQUIRED_AGE);
        } catch (ClassCastException e) {
            logger.debug("Could not parse required age for appId " + appId + " as integer. Trying string...", e);
            requiredAge = Integer.valueOf((String)dataMap.get(REQUIRED_AGE));
        }

        detailedDescription = (String)dataMap.get(DETAILED_DESCRIPTION);
        aboutTheGame = (String)dataMap.get(ABOUT_THE_GAME);

        String supportedLanguagesRaw = (String)dataMap.get(SUPPORTED_LANGUAGES);
        if (supportedLanguagesRaw != null) {
            supportedLanguages = Arrays.asList(supportedLanguagesRaw.split("\\s*,\\s*"));
        }

        headerImage = (String)dataMap.get(HEADER_IMAGE);
        website = (String)dataMap.get(WEBSITE);
    }

    @SuppressWarnings(UNCHECKED)
    private void parsePriceData(Map<Object, Object> dataMap) {
        Map<Object, Object> priceOverview = (Map<Object, Object>)dataMap.get(PRICE_OVERVIEW);

        if (priceOverview == null) {
            logger.info("No price data found. Assuming " + appId + " is free to play.");
            return;
        }

        price = new Price();
        price.setCurrency(Currency.getInstance((String)priceOverview.get(CURRENCY)));
        price.setInitialPrice(new BigDecimal(String.valueOf((Integer)priceOverview.get(INITIAL)))
            .divide(new BigDecimal(100)));
        price.setFinalPrice(new BigDecimal(String.valueOf((Integer)priceOverview.get(FINAL)))
            .divide(new BigDecimal(100)));
        price.setDiscountPercent((Integer)priceOverview.get(DISCOUNT_PERCENT));
    }

    @SuppressWarnings(UNCHECKED)
    private void parseMarketData(Map<Object, Object> dataMap) {
        developers = (List<String>)dataMap.get(DEVELOPERS);
        publishers = (List<String>)dataMap.get(PUBLISHERS);
    }

    @SuppressWarnings(UNCHECKED)
    private void parsePlatformData(Map<Object, Object> dataMap) {
        Map<Object, Object> platformsMap = (Map<Object, Object>)dataMap.get(PLATFORMS);

        availableForLinux = (Boolean)platformsMap.get(LINUX);
        availableForMac = (Boolean)platformsMap.get(MAC);
        availableForWindows = (Boolean)platformsMap.get(WINDOWS);
    }

    @SuppressWarnings(UNCHECKED)
    private void parseCategorieData(Map<Object, Object> dataMap) {
        categories = new ArrayList<Category>();

        List<Object> categoriesMap = (List<Object>)dataMap.get(CATEGORIES);
        if (categoriesMap == null) {
            logger.info("No categories data found for " + appId + " - " + name);
            return;
        }

        categoriesMap.stream().forEach((categoryObject) -> {
            Map<Object, Object> categoryItemMap = (Map<Object, Object>)categoryObject;

            String description = (String)categoryItemMap.get(DESCRIPTION);
            int id = Integer.parseInt((String)categoryItemMap.get(ID));

            categories.add(new Category(id, description));
        });
    }

    @SuppressWarnings(UNCHECKED)
    private void parseReleaseData(Map<Object, Object> dataMap) {
        Map<Object, Object> releaseMap = (Map<Object, Object>)dataMap.get(RELEASE_DATE);
        String dateString = (String)releaseMap.get(DATE);

        int numFields = dateString.split(" ").length;
        switch (numFields) {
        case 2:
            SimpleDateFormat sdf2 = new SimpleDateFormat("MMM yyyy", Locale.US);

            try {
                releaseDate = sdf2.parse(dateString);
            } catch (ParseException e) {
                logger.error("Could not parse release date for appId " + appId, e);
            }

            break;
        case 3:
            SimpleDateFormat sdf3 = new SimpleDateFormat("d MMM yyyy", Locale.US);

            try {
                releaseDate = sdf3.parse(dateString);
            } catch (ParseException e) {
                logger.error("Could not parse release date for appId " + appId, e);
            }

            break;
        default:
            logger.error("Unknown date pattern for " + appId + ". " + dateString);
        }
    }

    @SuppressWarnings(UNCHECKED)
    private void parseMetacriticData(Map<Object, Object> dataMap) {
        Map<Object, Object> metacriticMap = (Map<Object, Object>)dataMap.get(METACRITIC);

        if (metacriticMap == null) {
            logger.warn("No metacritic data found for " + appId + " - " + name);
            return;
        }

        metacriticScore = (Integer)metacriticMap.get(SCORE);
        metacriticUrl = (String)metacriticMap.get(URL);
    }

    @SuppressWarnings(UNCHECKED)
    private void parseSupportInfo(Map<Object, Object> dataMap) {
        Map<Object, Object> supportInfoMap = (Map<Object, Object>)dataMap.get(SUPPORT_INFO);
        supportInfo = new SupportInfo();

        String url = (String)supportInfoMap.get(URL);

        try {
            supportInfo.setUrl(new URL(url));
        } catch (MalformedURLException e) {
            logger.debug("Could not parse url " + url, e);
        }

        supportInfo.setEmail((String)supportInfoMap.get(EMAIL));
    }

    private Type assignType(String typeValue) {
        for (Type type : Type.values()) {
            if (type.getValue().equalsIgnoreCase(typeValue)) {
                return type;
            }
        }

        logger.warn("No type specified for " + typeValue);
        return Type.UNDEFINED;
    }

    public SteamApp build() {
        return new SteamApp(appId, type, name, requiredAge, detailedDescription, aboutTheGame, supportedLanguages,
            headerImage, website, price, developers, publishers, availableForLinux, availableForWindows,
            availableForMac, categories, releaseDate, metacriticScore, metacriticUrl, supportInfo);
    }
}
