package com.github.goive.steamapi.data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.goive.steamapi.enums.Type;
import com.github.goive.steamapi.exceptions.InvalidAppIdException;


public class SteamAppBuilder {

    private static final String DATA = "data";

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

    public SteamAppBuilder withSingleResultMap(Map<Object, Object> resultMap) {
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

    private static void parseGenericData(Map<Object, Object> dataMap) {
    }

    public SteamApp build() {

    }

}
