package com.github.goive.steamapi;

import com.github.goive.steamapi.client.ApiClient;
import com.github.goive.steamapi.client.ApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppSingleBuilder;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.util.Map;
import java.util.Set;

class SteamApiImpl implements SteamApi {

    private ApiClient client = new ApiClientImpl();

    private Set<Long> validAppIds;

    SteamApiImpl() {

    }

    public SteamApp retrieveApp(long appId) throws SteamApiException {
        if (validAppIds == null || validAppIds.isEmpty()) {
            validAppIds = client.retrieveValidAppIds();
        }

        if (validAppIds.contains(appId)) {
            Map<Object, Object> bodyMapForId = client.retrieveResultBodyMap(appId);
            return new SteamAppSingleBuilder().withResultMap(bodyMapForId).build();
        } else {
            throw new SteamApiException("Invalid appId given: " + appId, null);
        }
    }

    public void setCountryCode(CountryCode countryCode) {
        client.setCountryCode(countryCode);
    }

    public CountryCode getCountryCode() {
        return client.getCountryCode();
    }

    protected void setValidAppIds(Set<Long> validAppIds) {
        this.validAppIds = validAppIds;
    }

}
