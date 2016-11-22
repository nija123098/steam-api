package com.github.goive.steamapi.utils;

import com.github.goive.steamapi.exceptions.SteamApiException;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class AppIdMatcherUtilTest {

    private AppIdMatcherUtil util;
    private Map<Integer, String> appCache;

    @Before
    public void setup() {
        appCache = new HashMap<Integer, String>();

        appCache.put(1, "Counter-Strike: Condition Zero");
        appCache.put(2, "Half-Life: Opposing Force");
        appCache.put(3, "Half-Life: Counter-Strike");
        appCache.put(4, "Half-Life");
        appCache.put(5, "Arma 2");
        appCache.put(6, "Arma 2");
        appCache.put(7, "Momodora: Reverie Under the Moonlight");

        util = new AppIdMatcherUtil(appCache);
    }

    @Test
    public void shouldMatchExact() throws SteamApiException {
        String bestMatch = util.findBestMatch("Half-Life");

        assertEquals(appCache.get(4), bestMatch);
    }

    @Test
    public void shouldMatchContinuous() throws SteamApiException {
        String bestMatch = util.findBestMatch("Half-Life: O");

        assertEquals(appCache.get(2), bestMatch);
    }

    @Test
    public void shouldMatchFuzzy() throws SteamApiException {
        String bestMatch = util.findBestMatch("Half Life Opposing Force");

        assertEquals(appCache.get(2), bestMatch);
    }

    @Test
    public void shouldMatchContaining() throws SteamApiException {
        String bestMatch = util.findBestMatch("reverie");

        assertEquals(appCache.get(7), bestMatch);
    }

}
