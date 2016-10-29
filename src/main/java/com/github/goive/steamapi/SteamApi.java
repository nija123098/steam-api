package com.github.goive.steamapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBuilder;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class SteamApi {

    private static final String APP_ID_LIST_URL = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/";
    private static final String API_URL = "http://store.steampowered.com/api/appdetails?appids=";

    private ObjectMapper mapper = new ObjectMapper();
    private String countryCode;
    private Map<Integer, String> appCache = new HashMap<>();

    public SteamApi() {
        this(Locale.getDefault().getCountry());
    }

    public SteamApi(String countryCode) {
        setCountryCode(countryCode);
    }

    /**
     * Retrieves a steam game using its appId.
     *
     * @param appId The id of the game
     * @return {@link SteamApp} containing information of the game
     * @throws SteamApiException if invalid appId or service not available
     */
    public SteamApp retrieve(int appId) throws SteamApiException {
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

    private boolean successfullyRetrieved(Map resultBodyMap, int appId) {
        return (boolean) ((Map) resultBodyMap.get(String.valueOf(appId))).get("success");
    }

    /**
     * Retrieves a steam game using its app name. If multiple entries exist, only the first will be taken.
     *
     * @param appName The full or partial name of the game
     * @return {@link SteamApp} containing information of the game
     * @throws SteamApiException if no appId could be found or service not available
     */
    public SteamApp retrieve(String appName) throws SteamApiException {
        if (StringUtils.isBlank(appName)) {
            throw new IllegalArgumentException("appName cannot be empty");
        }

        if (appCache.isEmpty()) {
            buildAppCache();
        }

        int bestMatch = findBestMatch(appName);
        if (bestMatch != -1) {
            return retrieve(bestMatch);
        }

        throw new SteamApiException("No appId found for given app name: " + appName);
    }

    private int findBestMatch(String appName) {
        int currentLowestDistance = 1000;
        int currentBestMatchAppId = -1;

        for (int appId : appCache.keySet()) {
            int currentDistance;
            if ((currentDistance = StringUtils.getLevenshteinDistance(appCache.get(appId), appName)) < currentLowestDistance) {
                currentLowestDistance = currentDistance;
                currentBestMatchAppId = appId;
            }
        }

        return currentBestMatchAppId;
    }

    /**
     * Retrieves a list of all possible appIds.
     *
     * @return {@link List} of existing appIds
     * @throws SteamApiException if service not available
     */
    public List<Integer> listAppIds() throws SteamApiException {
        return listAppIds(false);
    }

    /**
     * Retrieves a list of all possible appIds.
     *
     * @param forceRefresh ensure that the newest ids are fetched
     * @return {@link List} of existing appIds
     * @throws SteamApiException if service not available
     */
    public List<Integer> listAppIds(boolean forceRefresh) throws SteamApiException {
        if (!appCache.isEmpty()) {
            return new ArrayList<>(appCache.keySet());
        }

        buildAppCache();

        return new ArrayList<>(appCache.keySet());
    }

    private void buildAppCache() throws SteamApiException {
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

        appCache = new HashMap<>();
        for (Object app : appList) {
            Map appItem = (Map) app;
            appCache.put((Integer) appItem.get("appid"), appItem.get("name").toString());
        }
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
