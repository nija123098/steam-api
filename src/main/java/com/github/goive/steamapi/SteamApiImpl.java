package com.github.goive.steamapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.github.goive.steamapi.client.SteamApiClient;
import com.github.goive.steamapi.client.SteamApiClientImpl;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBatchBuilder;
import com.github.goive.steamapi.data.SteamAppSingleBuilder;
import com.github.goive.steamapi.exceptions.SteamApiException;

public class SteamApiImpl implements SteamApi {

    public SteamApp retrieveData(long appId) throws SteamApiException {
        SteamApiClient client = new SteamApiClientImpl();

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
        SteamApiClient client = new SteamApiClientImpl();

        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(appIds);

        return new SteamAppBatchBuilder().withResultMap(resultBodyMap).build();
    }

}
