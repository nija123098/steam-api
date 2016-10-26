package com.github.goive.steamapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goive.steamapi.data.SteamId;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * The client that connects to the Steam API to retrieve data.
 *
 * @author Ivan Antes-Klobucar
 */
public class ApiClientImpl implements ApiClient {

    private static final String APP_ID_LIST_URL = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/";

    private String apiUrl = "http://store.steampowered.com/api/appdetails?appids=";
    private ObjectMapper mapper = new ObjectMapper();
    private String countryCode;

    public ApiClientImpl() {
        countryCode = Locale.getDefault().getCountry();
    }

    @Override
    public Map<Object, Object> retrieveResultBodyMap(SteamId app) throws SteamApiException {
        String appId = app.getAppId();
        Map resultBodyMap;

        try {
            URL src = new URL(apiUrl + appId + "&cc=" + countryCode);
            resultBodyMap = mapper.readValue(src, Map.class);
        } catch (IOException e) {
            throw new SteamApiException(e);
        }

        if (!successfullyRetrieved(resultBodyMap, appId)) {
            throw new SteamApiException("Invalid appId given: " + appId, null);
        }

        return resultBodyMap;
    }

    private boolean successfullyRetrieved(Map resultBodyMap, String appId) {
        return (boolean) ((Map) resultBodyMap.get(String.valueOf(appId))).get("success");
    }

    @Override
    public List<SteamId> retrieveAllAppIds() throws SteamApiException {
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

        List<SteamId> result = new ArrayList<>();
        for (Object app : appList) {
            Map appItem = (Map) app;
            result.add(SteamId.create(appItem.get("appid").toString(), appItem.get("name").toString()));
        }

        return result;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String getCountryCode() {
        return countryCode;
    }

    public <M extends ObjectMapper> void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}
