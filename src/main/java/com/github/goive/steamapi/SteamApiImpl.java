package com.github.goive.steamapi;

import com.github.goive.steamapi.client.ApiClient;
import com.github.goive.steamapi.client.ApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppSingleBuilder;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

import java.util.Map;

public class SteamApiImpl implements SteamApi {

    private ApiClient client = new ApiClientImpl();

    SteamApiImpl() {

    }

    public SteamApp retrieveApp(long appId) throws SteamApiException {
        Map<Object, Object> bodyMapForId = client.retrieveResultBodyMap(appId);
        return new SteamAppSingleBuilder().withResultMap(bodyMapForId).build();
    }

    public void setCountryCode(CountryCode countryCode) {
        client.setCountryCode(countryCode);
    }
    
    public CountryCode getCountryCode(){
        return client.getCountryCode();
    }

}
