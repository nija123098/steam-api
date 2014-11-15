package com.github.goive.steamapi;

import com.github.goive.steamapi.client.SteamApiClient;
import com.github.goive.steamapi.client.SteamApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppSingleBuilder;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.util.Map;

public class SteamApiImpl implements SteamApi {

    private SteamApiClient client = new SteamApiClientImpl();

    public SteamApp retrieveData(long appId) throws SteamApiException {
        Map<Object, Object> bodyMapForId = client.retrieveResultBodyMap(appId);

        return new SteamAppSingleBuilder().withResultMap(bodyMapForId).build();
    }

    @Override
    public void setCountryCode(CountryCode countryCode) {
        client.setCountryCode(countryCode);
    }

    @Override
    public CountryCode getCountryCode() {
        return client.getCountryCode();
    }

}
