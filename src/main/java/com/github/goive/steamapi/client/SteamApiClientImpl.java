package com.github.goive.steamapi.client;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

/**
 * The client that connects to the Steam API to retrieve the data for the given appId.
 * 
 * @author Ivan Antes-Klobucar
 * @version 2.1
 */
public class SteamApiClientImpl implements SteamApiClient {

    private String apiUrl = "http://store.steampowered.com/api/appdetails?appids=";
    private final ObjectMapper mapper = new ObjectMapper();
    private CountryCode countryCode;

    public SteamApiClientImpl() {
        countryCode = CountryCode.AT;
    }

    /**
     * Creates a new instance.
     * 
     * @param customApiUrl Overrides the standard API url
     */
    public SteamApiClientImpl(String customApiUrl) {
        this();
        this.apiUrl = customApiUrl;
    }

    /**
     * Creates a new instance.
     * 
     * @param countryCode Overrides the default country code to return the correct currency.
     */
    public SteamApiClientImpl(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

    @SuppressWarnings("unchecked")
    private Map<Object, Object> fetchResultMap(String appIds) {
        Map<Object, Object> resultMap;

        try {
            URL src = new URL(apiUrl + appIds + "&cc=" + countryCode.name());
            resultMap = mapper.readValue(src, Map.class);
        } catch (IOException e) {
            throw new SteamApiException(e);
        }

        return resultMap;
    }

    @Override
    public Map<Object, Object> retrieveResultBodyMap(long appId) throws SteamApiException {
        return fetchResultMap(appId + "");
    }

    public CountryCode getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(CountryCode countryCode) {
        this.countryCode = countryCode;
    }

}
