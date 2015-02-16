package com.github.goive.steamapi;

import com.github.goive.steamapi.client.AppIdChecker;
import com.github.goive.steamapi.client.SteamApiClient;
import com.github.goive.steamapi.client.SteamApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppSingleBuilder;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.InvalidAppIdException;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.util.Map;

public class SteamApiImpl implements SteamApi {

    private SteamApiClient client = new SteamApiClientImpl();

    SteamApiImpl() {

    }

    public SteamApp retrieveApp(long appId) throws SteamApiException {
        AppIdChecker appIdChecker = new AppIdChecker();
        if (appIdChecker.existsAppIdOnSteam(appId)) {
            Map<Object, Object> bodyMapForId = client.retrieveResultBodyMap(appId);

            return new SteamAppSingleBuilder().withResultMap(bodyMapForId).build();
        }

        throw new InvalidAppIdException(appId + "");
    }

    public void setCountryCode(CountryCode countryCode) {
        client.setCountryCode(countryCode);
    }

    public CountryCode getCountryCode() {
        return client.getCountryCode();
    }

}
