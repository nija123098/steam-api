package com.github.goive.steamapi.builders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goive.steamapi.data.Category;
import com.github.goive.steamapi.data.Price;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBuilder;
import com.github.goive.steamapi.enums.Type;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Map;

public class SteamAppBuilderTest {

    private static final long HALF_LIFE_APP_ID = 70L;
    private Map<Object, Object> halfLifeResultMap;
    private Map<Object, Object> halfLifeResultMapWithTwoFieldReleaseDate;
    private Map<Object, Object> freeToPlayResultMap;
    private Map<Object, Object> oneDigitReleaseDayResultMap;
    private Map<Object, Object> failResultMap;
    private Map<Object, Object> dlcResultMap;

    private SteamAppBuilder builder = new SteamAppBuilder();

    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        halfLifeResultMap = objectMapper.readValue(new File("src/test/resources/app_id_70.json"), Map.class);
        halfLifeResultMapWithTwoFieldReleaseDate = objectMapper.readValue(new File("src/test/resources/app_id_70_2_field_release_date.json"), Map.class);
        freeToPlayResultMap = objectMapper.readValue(new File("src/test/resources/f2p_game.json"), Map.class);
        oneDigitReleaseDayResultMap = objectMapper.readValue(new File("src/test/resources/one_digit_release_day.json"), Map.class);
        failResultMap = objectMapper.readValue(new File("src/test/resources/app_id_fail.json"), Map.class);
        dlcResultMap = objectMapper.readValue(new File("src/test/resources/app_type_dlc.json"), Map.class);
    }

    @Test
    public void shouldCreateSteamApp() throws SteamApiException {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        Assert.assertNotNull(steamApp);
    }

    @Test
    public void shouldContainCorrectPriceData() throws SteamApiException {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();
        Price price = steamApp.getPrice();

        Assert.assertEquals("Currency not correct", Currency.getInstance("EUR"), price.getCurrency());
        Assert.assertEquals("Initial price not correct", BigDecimal.valueOf(9.99), price.getInitialPrice());
        Assert.assertEquals("Final price not correct", BigDecimal.valueOf(4.99), price.getFinalPrice());
        Assert.assertEquals("Discount percent not correct", 50, price.getDiscountPercent());
    }

    @Test
    public void shouldContainCorrectSupportedLanguages() throws SteamApiException {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        Assert.assertEquals("SupportedLanguages size not correct", 8, steamApp.getSupportedLanguages().size());
        Assert.assertTrue(steamApp.getSupportedLanguages().contains("Italian"));
        Assert.assertTrue(steamApp.getSupportedLanguages().contains("French"));
    }

    @Test
    public void shouldContainCorrectGenericData() throws SteamApiException {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        Assert.assertEquals("AppId not correct", HALF_LIFE_APP_ID, steamApp.getAppId());
        Assert.assertEquals("Type not correct", Type.GAME, steamApp.getType());
        Assert.assertEquals("Name not correct", "Half-Life", steamApp.getName());
        Assert.assertEquals("RequiredAge not correct", 10, steamApp.getRequiredAge());
        Assert.assertTrue("DetailedDescription not correct", steamApp.getDetailedDescription().contains("Named Game of the Year by over 50"));
        Assert.assertTrue("AboutTheGame not correct", steamApp.getAboutTheGame().contains("Valve's debut title blends action and adventure"));
        Assert.assertEquals("HeaderImage not correct", "http://cdn.akamai.steamstatic.com/steam/apps/70/header.jpg?t=1360355165", steamApp.getHeaderImage());
        Assert.assertEquals("Website not correct", "http://www.half-life.com/", steamApp.getWebsite());
    }

    @Test
    public void shouldContainCorrectMarketInfo() {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        Assert.assertEquals("Not all developers are present", 1, steamApp.getDevelopers().size());
        Assert.assertTrue(steamApp.getDevelopers().contains("Valve"));

        Assert.assertEquals("Not all publishers are present", 1, steamApp.getPublishers().size());
        Assert.assertTrue(steamApp.getPublishers().contains("Valve"));
    }

    @Test
    public void shouldContainCorrectPlatformData() {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        Assert.assertTrue("Linux availability", steamApp.isAvailableForLinux());
        Assert.assertTrue("Windows availability", steamApp.isAvailableForWindows());
        Assert.assertTrue("Mac availability", steamApp.isAvailableForMac());
    }

    @Test
    public void shouldContainCorrectCategories() {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        Assert.assertEquals("Incorrect amount of categories", 3, steamApp.getCategories().size());

        Category singlePlayerCategory = new Category(2, "Single-player");
        Assert.assertTrue("Wrong category", steamApp.getCategories().contains(singlePlayerCategory));
    }

    @Test
    public void shouldContainReleaseDataWhenThreeFields() throws ParseException {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date releaseDate = sdf.parse("8/11/1998");

        Assert.assertEquals("Release date not correct", releaseDate, steamApp.getReleaseDate());
    }

    @Test
    public void shouldContainReleaseDataWhenTwoFields() throws ParseException {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMapWithTwoFieldReleaseDate).build();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");
        Date releaseDate = sdf.parse("11/1998");

        Assert.assertEquals("Release date not correct", releaseDate, steamApp.getReleaseDate());
    }

    @Test
    public void shouldHandleFreeToPlayGame() {
        SteamApp steamApp = builder.withResultMap(freeToPlayResultMap).build();

        Assert.assertNull(steamApp.getPrice());
    }

    @Test
    public void shouldHandleOneDigitReleaseDate() {
        SteamApp steamApp = builder.withResultMap(oneDigitReleaseDayResultMap).build();

        Assert.assertNotNull(steamApp.getReleaseDate());
    }

    @Test
    public void shouldContainMetacriticData() {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        Assert.assertEquals("Metacritic score not correct", Integer.valueOf(96), steamApp.getMetacriticScore());
        Assert.assertEquals("Metacritic url not correct", "http://www.metacritic.com/game/pc/half-life", steamApp.getMetacriticUrl());
    }

    @Test
    public void shouldContainSupportInfo() {
        SteamApp steamApp = builder.withResultMap(halfLifeResultMap).build();

        Assert.assertNotNull(steamApp.getSupportInfo());
        Assert.assertEquals("URL not correct", "http://www.google.com", steamApp.getSupportInfo().getUrl().toString());
    }

    @Test(expected = SteamApiException.class)
    public void shouldThrowExceptionOnInvalidAppId() {
        builder.withResultMap(failResultMap).build();
    }

    @Test
    public void shouldContainDclAppType() {
        SteamApp steamApp = builder.withResultMap(dlcResultMap).build();

        Assert.assertEquals(Type.DLC, steamApp.getType());
    }
}
