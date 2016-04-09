package com.github.goive.steamapi;

import com.github.goive.steamapi.client.ApiClient;
import com.github.goive.steamapi.client.ApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBuilder;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.util.Map;

class SteamApiImpl implements SteamApi {

    private ApiClient client = new ApiClientImpl();

    SteamApiImpl() {

    }

    public SteamApp retrieveApp(long appId) throws SteamApiException {
        Map<Object, Object> bodyMapForId = client.retrieveResultBodyMap(appId);
        return new SteamAppBuilder().withResultMap(bodyMapForId).build();
    }

    public void setCountryCode(CountryCode countryCode) {
        client.setCountryCode(countryCode);
    }

    public CountryCode getCountryCode() {
        return client.getCountryCode();
    }

}
