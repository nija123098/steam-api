package e2e.client;

import com.github.goive.steamapi.data.SteamId;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by ive on 17.02.15.
 */
public class ApiClientRetrieveValidAppIdsTest extends AbstractApiClientTest {

    @Test
    public void shouldSuccessfullyRetrieveValidAppIds() throws SteamApiException {
        List<SteamId> appIds = steamApi.listIds();

        // 15721 AppIds as of 17.02.2015
        Assert.assertTrue(appIds.size() > 15000);
    }

}
