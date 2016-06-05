package com.github.goive.steamapi;

import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamId;
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
     * @deprecated Use retrieveApp({@link SteamId} app) instead.
     */
    @Deprecated
    SteamApp retrieveApp(long appId) throws SteamApiException;

    /**
     * Retrieves a {@link SteamApp} object for the given appId.
     *
     * @param app The {@link SteamId} for the application.
     * @return {@link SteamApp} object containing information about the given appId.
     * @throws SteamApiException if the retrieving went wrong or the appId is invalid.
     */
    SteamApp retrieveApp(SteamId app) throws SteamApiException;

    CountryCode getCountryCode();

}
