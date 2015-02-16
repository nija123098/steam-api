package e2e;

import e2e.client.AppIdChecker;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ive on 14.02.15.
 */
public class AppIdCheckerTest {

    private AppIdChecker appIdChecker = new AppIdChecker();

    @Test
    public void testWithValidAppId(){
        Assert.assertTrue(appIdChecker.existsAppIdOnSteam(70L));
    }

    @Test
    public void testWithInvalidAppId(){
        Assert.assertFalse(appIdChecker.existsAppIdOnSteam(999999999L));
    }

}
