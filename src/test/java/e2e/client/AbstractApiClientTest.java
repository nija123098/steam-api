package e2e.client;

import com.github.goive.steamapi.SteamApi;
import org.junit.Before;

/**
 * Created by ive on 17.02.15.
 */
public abstract class AbstractApiClientTest {

    SteamApi steamApi;

    @Before
    public void setup() {
        steamApi = new SteamApi();
    }

}
