package com.github.goive.steamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.goive.steamapi.client.SteamApiClient;
import com.github.goive.steamapi.client.SteamApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBatchBuilder;
import com.github.goive.steamapi.data.SteamAppSingleBuilder;
import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

public class SteamApiImpl implements SteamApi {

    private SteamApiClient client = new SteamApiClientImpl();

    public SteamApp retrieveData(long appId) throws SteamApiException {
        Map<Object, Object> bodyMapForId = client.retrieveResultBodyMap(appId);

        return new SteamAppSingleBuilder().withResultMap(bodyMapForId).build();
    }

    public List<SteamApp> retrieveData(long... appIds) throws SteamApiException {
        List<Long> longList = new ArrayList<Long>();
        for (long appId : appIds) {
            longList.add(appId);
        }
        return this.retrieveData(longList);
    }

    public List<SteamApp> retrieveData(List<Long> appIds) throws SteamApiException {
        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(appIds);

        return new SteamAppBatchBuilder().withResultMap(resultBodyMap).build();
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
