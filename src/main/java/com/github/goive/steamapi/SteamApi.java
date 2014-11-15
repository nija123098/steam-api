package com.github.goive.steamapi;

import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

/**
 * Entry point for the APIs' external use.
 * 
 * @author Ivan Antes-Klobucar
 * @version 2.2
 */
public interface SteamApi {

    /**
     * Retrieves a {@link SteamApp} object for the given appId.
     * 
     * @param appId Unique ID of the Steam application (visible in URL on Steam store page)
     * @return {@link SteamApp} object containing information about the given appId.
     * @throws SteamApiException if the retrieving went wrong or the appId is invalid.
     */
    SteamApp retrieveData(long appId) throws SteamApiException;

    /**
     * Sets the country code to use for the API call. This code determines the returned price and currency.
     * 
     * @param countryCode {@link CountryCode} for the call.
     */
    void setCountryCode(CountryCode countryCode);

    /**
     * Returns the currently set {@link CountryCode}. Calls to the API will return the currency based on this code.
     * 
     * @return country code
     */
    CountryCode getCountryCode();

}
