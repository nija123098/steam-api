package com.github.goive.steamapi;

import com.github.goive.steamapi.enums.CountryCode;

/**
 * Created by ive on 16.02.15.
 */
public class SteamApiFactory {

    /**
     * Creates an instance of the SteamApi that fetches data from the default (AT) region.
     *
     * @return {@link com.github.goive.steamapi.SteamApi} object to interact with the storefront api
     */
    public static SteamApi getSteamApi() {
        return getSteamApi(CountryCode.AT);
    }

    /**
     * Creates an instance of the SteamApi that fetches data from the specified region.
     *
     * @return {@link com.github.goive.steamapi.SteamApi} object to interact with the storefront api
     */
    public static SteamApi getSteamApi(CountryCode countryCode) {
        SteamApiImpl instance = new SteamApiImpl();
        instance.setCountryCode(countryCode);
        return instance;
    }

}
