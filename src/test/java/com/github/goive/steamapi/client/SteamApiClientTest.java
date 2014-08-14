package com.github.goive.steamapi.client;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.github.goive.steamapi.enums.CountryCode;
import com.github.goive.steamapi.exceptions.SteamApiException;

public class SteamApiClientTest extends AbstractSteamApiClientTest {

    @Test
    public void shouldSuccessfullyRetrieveResultBodyMapFromSteamWithOneId() throws SteamApiException {
        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(HALF_LIFE_APP_ID);

        Assert.assertNotNull(resultBodyMap);
        Assert.assertTrue(resultBodyMap.containsKey(String.valueOf(HALF_LIFE_APP_ID)));
    }

    @Test
    public void shouldFailToRetrieveResultBodyMapFromSteamWithOneId() throws SteamApiException {
        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(NOT_EXISTING_ID);

        Assert.assertNotNull(resultBodyMap);
        Assert.assertTrue(resultBodyMap.containsKey(String.valueOf(NOT_EXISTING_ID)));
    }

    @Test(expected = SteamApiException.class)
    public void shouldFailToCallSteamApi() throws SteamApiException {
        client = new SteamApiClientImpl("invalidurl");
        client.retrieveResultBodyMap(HALF_LIFE_APP_ID);
    }

    @Test
    public void shouldRetrieveCorrectCurrencyForCountryCodeUS() {
        client = new SteamApiClientImpl();
        client.setCountryCode(CountryCode.US);

        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(10180L);

        Assert.assertTrue(resultBodyMap.toString().contains("currency=USD"));
    }

    @Test
    public void shouldRetrieveCorrectCurrencyForCountryCodeRU() {
        client = new SteamApiClientImpl();
        client.setCountryCode(CountryCode.RU);

        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(10180L);

        Assert.assertTrue(resultBodyMap.toString().contains("currency=RUB"));
    }

}
