package com.github.goive.steamapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBuilder;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SteamApi {

    private static final String APP_ID_LIST_URL = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/";
    private static final String API_URL = "http://store.steampowered.com/api/appdetails?appids=";

    private ObjectMapper mapper = new ObjectMapper();

    private String countryCode;

    public SteamApi() {
        this(Locale.getDefault().getCountry());
    }

    public SteamApi(String countryCode) {
        setCountryCode(countryCode);
    }

    public SteamApp retrieve(String appId) throws SteamApiException {
        Map resultBodyMap;

        try {
            URL src = new URL(API_URL + appId + "&cc=" + countryCode);
            resultBodyMap = mapper.readValue(src, Map.class);
        } catch (IOException e) {
            throw new SteamApiException(e);
        }

        if (!successfullyRetrieved(resultBodyMap, appId)) {
            throw new SteamApiException("Invalid appId given: " + appId);
        }


        return new SteamAppBuilder().withResultMap(resultBodyMap).build();
    }

    private boolean successfullyRetrieved(Map resultBodyMap, String appId) {
        return (boolean) ((Map) resultBodyMap.get(String.valueOf(appId))).get("success");
    }

    public List<String> listIds() throws SteamApiException {
        Map<Object, Object> resultMap;
        try {
            URL src = new URL(APP_ID_LIST_URL);
            resultMap = mapper.readValue(src, Map.class);
        } catch (IOException e) {
            throw new SteamApiException("Error fetching list of valid AppIDs.", e);
        }

        Map appMap = (Map) resultMap.get("applist");
        Map apps = (Map) appMap.get("apps");
        List appList = (List) apps.get("app");

        List<String> result = new ArrayList<>();
        for (Object app : appList) {
            Map appItem = (Map) app;
            result.add(appItem.get("appid").toString());
        }

        return result;
    }

    public void setCountryCode(String countryCode) {
        if (StringUtils.isBlank(countryCode)) {
            throw new IllegalArgumentException("Country code cannot be empty.");
        }

        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

}
