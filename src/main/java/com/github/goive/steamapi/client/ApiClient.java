package com.github.goive.steamapi.client;

import java.util.Map;
import java.util.Set;

import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

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
     * @throws SteamApiException
     */
    Map<Object, Object> retrieveResultBodyMap(long appId) throws SteamApiException;

    /**
     * Retrieves the list of valid appIds from Steam.
     *
     * @return Set of unique appIds that can be queried by this API.
     * @throws {@link com.github.goive.steamapi.exceptions.SteamApiException}
     */
    Set<Long> retrieveValidAppIds() throws SteamApiException;

    public void setCountryCode(CountryCode countryCode);

    CountryCode getCountryCode();
}
