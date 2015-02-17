package com.github.goive.steamapi;

import com.github.goive.steamapi.enums.CountryCode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by ive on 17.02.15.
 */
public class SteamApiFactoryTest {

    @Test
    public void shouldReturnValidSteamApi() {
        SteamApi steamApi = SteamApiFactory.createSteamApi();

        assertNotNull(steamApi);
    }

    @Test
    public void shouldReturnValidSteamApiWithCountryCode() {
        SteamApi steamApi = SteamApiFactory.createSteamApi(CountryCode.RU);

        assertNotNull(steamApi);
        assertEquals("Wrong country code", CountryCode.RU, steamApi.getCountryCode());
    }

}
