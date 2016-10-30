package com.github.goive.steamapi;

import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;

public class SteamApiCompatibilityTest {

    private static final int HALF_LIFE_APP_ID = 70;
    private static final int NOT_EXISTING_ID = 709999999;
    private static final int CURRENCY_ID = 10180;

    private SteamApi steamApi;

    @Before
    public void setup() {
        steamApi = new SteamApi();
    }

    @Test
    public void shouldSuccessfullyRetrieveValidSteamApp() throws SteamApiException {
        steamApi.setCountryCode("AT");

        SteamApp halfLife = steamApi.retrieve(HALF_LIFE_APP_ID);

        assertNotNull(halfLife);
        assertEquals("Name", "Half-Life", halfLife.getName());
        assertEquals("Type", "game", halfLife.getType());
        assertEquals("Required Age", 0, halfLife.getRequiredAge());
        assertEquals("F2P", false, halfLife.isFreeToPlay());
        assertEquals("Detailed Description", "Named Game of the Year by over 50 publications, Valve's debut title " +
                "blends action and adventure with award-winning technology to create a frighteningly realistic world " +
                "where players must think to survive. Also includes an exciting multiplayer mode that allows you to " +
                "play against friends and enemies around the world.", halfLife.getDetailedDescription());
        assertEquals("About", "Named Game of the Year by over 50 publications, Valve's debut title blends action and " +
                "adventure with award-winning technology to create a frighteningly realistic world where players " +
                "must think to survive. Also includes an exciting multiplayer mode that allows you to play against " +
                "friends and enemies around the world.", halfLife.getAboutTheGame());
        assertEquals("Languages", Arrays.asList("English", "French", "German", "Italian", "Spanish",
                "Simplified Chinese", "Traditional Chinese", "Korean"), halfLife.getSupportedLanguages());
        assertEquals("Header Image", "http://cdn.akamai.steamstatic.com/steam/apps/70/header.jpg?t=1447890508",
                halfLife.getHeaderImage());
        assertEquals("Website", "http://www.half-life.com/", halfLife.getWebsite());
        assertEquals("Developers", Arrays.asList("Valve"), halfLife.getDevelopers());
        assertEquals("Publishers", Arrays.asList("Valve"), halfLife.getPublishers());
        assertNotNull("Has price", halfLife.getPrice());
        assertTrue("Final Price over 0", halfLife.getPrice().getFinalPrice().compareTo(BigDecimal.ONE) == 1);
        assertTrue("Initial Price over 0", halfLife.getPrice().getInitialPrice().compareTo(BigDecimal.ONE) == 1);
        assertTrue("Windows", halfLife.isAvailableForWindows());
        assertTrue("Linux", halfLife.isAvailableForLinux());
        assertTrue("Mac", halfLife.isAvailableForMac());
        assertEquals("Categories", Arrays.asList("Single-player", "Multi-player", "Valve Anti-Cheat enabled"),
                halfLife.getCategories());
        assertEquals("Release Date", new GregorianCalendar(1998, Calendar.NOVEMBER, 8).getTime(),
                halfLife.getReleaseDate());
        assertEquals("Support URL", "http://steamcommunity.com/app/70", halfLife.getSupportUrl());
        assertEquals("Support Email", "", halfLife.getSupportEmail());
        assertEquals("Genres", Arrays.asList("Action"), halfLife.getGenres());
    }

    @Test(expected = SteamApiException.class)
    public void shouldFailToRetrieveSteamAppWithInvalidAppId() throws SteamApiException {
        steamApi.retrieve(NOT_EXISTING_ID);
    }

    @Test
    public void shouldRetrieveCorrectCurrencyForCountryCodeUS() throws SteamApiException {
        steamApi.setCountryCode("US");

        SteamApp steamApp = steamApi.retrieve(CURRENCY_ID);

        assertEquals(Currency.getInstance("USD"), steamApp.getPrice().getCurrency());
    }

    @Test
    public void shouldRetrieveCorrectCurrencyForCountryCodeRU() throws SteamApiException {
        steamApi.setCountryCode("RU");

        SteamApp steamApp = steamApi.retrieve(CURRENCY_ID);

        assertEquals(Currency.getInstance("RUB"), steamApp.getPrice().getCurrency());
    }

    @Test
    public void shouldSuccessfullyRetrieveValidAppIds() throws SteamApiException {
        Map<Integer, String> appIds = steamApi.listApps();

        // 15721 AppIds as of 17.02.2015
        assertTrue(appIds.size() > 15000);
    }

    @Test
    public void shouldRetrieveSteamAppByName() throws SteamApiException {
        steamApi.setCountryCode("US");

        SteamApp steamApp = steamApi.retrieve("Half-Life");
        assertEquals(steamApp.getName(), "Half-Life");
    }
}
