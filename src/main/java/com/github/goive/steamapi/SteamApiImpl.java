package com.github.goive.steamapi;

import com.github.goive.steamapi.client.ApiClient;
import com.github.goive.steamapi.client.ApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBuilder;
import com.github.goive.steamapi.data.SteamId;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.util.List;
import java.util.Map;

class SteamApiImpl implements SteamApi {

    private ApiClient client = new ApiClientImpl();

    SteamApiImpl() {

    }

    @Override
    @Deprecated
    public SteamApp retrieveApp(long appId) throws SteamApiException {
        return retrieveApp(SteamId.create(appId));
    }

    @Override
    public SteamApp retrieveApp(SteamId app) throws SteamApiException {
        Map<Object, Object> bodyMapForId = client.retrieveResultBodyMap(app);
        return new SteamAppBuilder().withResultMap(bodyMapForId).build();
    }

    @Override
    public List<SteamId> retrievePossibleSteamIds() {
        return client.retrieveAllAppIds();
    }

    public void setCountryCode(CountryCode countryCode) {
        client.setCountryCode(countryCode);
    }

    public CountryCode getCountryCode() {
        return client.getCountryCode();
    }

}
