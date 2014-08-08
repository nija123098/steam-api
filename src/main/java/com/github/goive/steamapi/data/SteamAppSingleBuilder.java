package com.github.goive.steamapi.data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
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

    public SteamAppSingleBuilder withSingleResultMap(Map<Object, Object> resultMap) {
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
    }

    private void parseGenericData(Map<Object, Object> dataMap) {
        String type = (String)dataMap.get(TYPE);
        Type appType = assignType(type);
        this.type = appType;

        String name = (String)dataMap.get(NAME);
        steamApp.setName(name);

        Integer requiredAge = null;

        try {
            requiredAge = (Integer)dataMap.get(REQUIRED_AGE);
        } catch (ClassCastException e) {
            logger.debug("Could not parse required age for appId " + steamApp.getAppId()
                + " as integer. Trying string...", e);
            requiredAge = Integer.valueOf((String)dataMap.get(REQUIRED_AGE));
        }

        steamApp.setRequiredAge(requiredAge);

        String detailedDescription = (String)dataMap.get(DETAILED_DESCRIPTION);
        steamApp.setDetailedDescription(detailedDescription);

        String aboutTheGame = (String)dataMap.get(ABOUT_THE_GAME);
        steamApp.setAboutTheGame(aboutTheGame);

        String supportedLanguagesRaw = (String)dataMap.get(SUPPORTED_LANGUAGES);
        if (supportedLanguagesRaw != null) {
            List<String> supportedLanguages = Arrays.asList(supportedLanguagesRaw.split("\\s*,\\s*"));
            steamApp.setSupportedLanguages(supportedLanguages);
        }

        String headerImage = (String)dataMap.get(HEADER_IMAGE);
        steamApp.setHeaderImage(headerImage);

        String website = (String)dataMap.get(WEBSITE);
        steamApp.setWebsite(website);
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

    }

}
