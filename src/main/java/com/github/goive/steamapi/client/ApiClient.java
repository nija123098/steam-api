package com.github.goive.steamapi.client;

import com.github.goive.steamapi.data.SteamId;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.util.List;
import java.util.Map;

/**
 * Specifies a client implementation of the Steam store API.
 *
 * @author Ivan Antes-Klobucar
 * @version 1.1
 */
public interface ApiClient {

    /**
     * Retrieves a {@link Map} representing the JSON structure of the response.
     *
     * @param appId Unique ID of the Steam application (visible in URL on Steam store page)
     * @return Map representation of the JSON object returned by the API
     * @throws SteamApiException in case of invalid appId
     */
    Map<Object, Object> retrieveResultBodyMap(SteamId appId) throws SteamApiException;

    List<SteamId> retrieveAllAppIds() throws SteamApiException;

    void setCountryCode(String countryCode);

    String getCountryCode();
}
