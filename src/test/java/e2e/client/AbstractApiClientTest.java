package e2e.client;

import com.github.goive.steamapi.client.ApiClientImpl;
import org.junit.Before;

import java.io.IOException;

/**
 * Created by ive on 17.02.15.
 */
public abstract class AbstractApiClientTest {

    ApiClientImpl client;

    @Before
    public void setup() throws IOException {
        client = new ApiClientImpl();
    }

}
