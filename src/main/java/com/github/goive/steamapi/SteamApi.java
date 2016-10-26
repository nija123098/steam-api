package com.github.goive.steamapi;

import com.github.goive.steamapi.client.ApiClient;
import com.github.goive.steamapi.client.ApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBuilder;
import com.github.goive.steamapi.data.SteamId;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.util.List;
import java.util.Locale;
import java.util.Map;

class SteamApi {

    private ApiClient client = new ApiClientImpl();

    public SteamApi() {
        this(Locale.getDefault().getCountry());
    }

    public SteamApi(String countryCode) {
        setCountryCode(countryCode);
    }

    public SteamApp retrieveApp(SteamId app) throws SteamApiException {
        Map<Object, Object> bodyMapForId = client.retrieveResultBodyMap(app);
        return new SteamAppBuilder().withResultMap(bodyMapForId).build();
    }

    public List<SteamId> retrievePossibleSteamIds() {
        return client.retrieveAllAppIds();
    }

    public void setCountryCode(String countryCode) {
        client.setCountryCode(countryCode);
    }

    public String getCountryCode() {
        return client.getCountryCode();
    }

}
