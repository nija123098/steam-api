package e2e.client;

import com.github.goive.steamapi.data.SteamId;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ApiClientRetrieveDataForAppIdTest extends AbstractApiClientTest {

    private static final SteamId HALF_LIFE_APP_ID = SteamId.create(70L);
    private static final SteamId NOT_EXISTING_ID = SteamId.create(7099999999999L);
    private static final SteamId CURRENCY_ID = SteamId.create(10180L);

    @Test
    public void shouldSuccessfullyRetrieveResultBodyMapFromSteamWithOneId() throws SteamApiException {
        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(HALF_LIFE_APP_ID);

        Assert.assertNotNull(resultBodyMap);
        Assert.assertTrue(resultBodyMap.containsKey(String.valueOf(HALF_LIFE_APP_ID.getAppId())));
    }

    @Test(expected = SteamApiException.class)
    public void shouldFailToRetrieveResultBodyMapFromSteamWithOneId() throws SteamApiException {
        client.retrieveResultBodyMap(NOT_EXISTING_ID);
    }

    @Test(expected = SteamApiException.class)
    public void shouldFailToCallSteamApi() throws SteamApiException {
        client.setApiUrl("invalidurl");
        client.retrieveResultBodyMap(HALF_LIFE_APP_ID);
    }

    @Test
    public void shouldRetrieveCorrectCurrencyForCountryCodeUS() {
        client.setCountryCode("US");

        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(CURRENCY_ID);

        Assert.assertTrue(resultBodyMap.toString().contains("currency=USD"));
    }

    @Test
    public void shouldRetrieveCorrectCurrencyForCountryCodeRU() {
        client.setCountryCode("RU");

        Map<Object, Object> resultBodyMap = client.retrieveResultBodyMap(CURRENCY_ID);

        Assert.assertTrue(resultBodyMap.toString().contains("currency=RUB"));
    }

}
