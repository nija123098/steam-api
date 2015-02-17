package e2e.client;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Created by ive on 17.02.15.
 */
public class ApiClientRetrieveValidAppIdsTest extends AbstractApiClientTest {

    @Test
    public void shouldSuccessfullyRetrieveValidAppIds() {
        Set<Long> appIds = client.retrieveValidAppIds();

        // 15721 AppIds as of 17.02.2015
        Assert.assertTrue(appIds.size() > 15000);
    }

}
