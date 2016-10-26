package e2e.client;

import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamId;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Currency;

public class ApiClientRetrieveDataForAppIdTest extends AbstractApiClientTest {

    private static final SteamId HALF_LIFE_APP_ID = SteamId.create(70L);
    private static final SteamId NOT_EXISTING_ID = SteamId.create(7099999999999L);
    private static final SteamId CURRENCY_ID = SteamId.create(10180L);

    @Test
    public void shouldSuccessfullyRetrieveResultBodyMapFromSteamWithOneId() throws SteamApiException {
        SteamApp halfLife = steamApi.retrieve(HALF_LIFE_APP_ID);

        Assert.assertNotNull(halfLife);
        Assert.assertEquals("Name", "Half-Life", halfLife.getName());
    }

    @Test(expected = SteamApiException.class)
    public void shouldFailToRetrieveResultBodyMapFromSteamWithOneId() throws SteamApiException {
        steamApi.retrieve(NOT_EXISTING_ID);
    }

    @Test
    public void shouldRetrieveCorrectCurrencyForCountryCodeUS() throws SteamApiException {
        steamApi.setCountryCode("US");

        SteamApp steamApp = steamApi.retrieve(CURRENCY_ID);

        Assert.assertEquals(Currency.getInstance("USD"), steamApp.getPrice().getCurrency());
    }

    @Test
    public void shouldRetrieveCorrectCurrencyForCountryCodeRU() throws SteamApiException {
        steamApi.setCountryCode("RU");

        SteamApp steamApp = steamApi.retrieve(CURRENCY_ID);

        Assert.assertEquals(Currency.getInstance("RUB"), steamApp.getPrice().getCurrency());
    }

}
