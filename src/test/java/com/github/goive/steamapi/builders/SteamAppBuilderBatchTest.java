package com.github.goive.steamapi.builders;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBatchBuilder;

public class SteamAppBuilderBatchTest extends AbstractSteamAppBuilderTest {

    private SteamAppBatchBuilder builder = new SteamAppBatchBuilder();

    @Test
    public void shouldCreateSteamAppsFromTwoSuccessfullyRetrievedApps() {
        List<SteamApp> apps = builder.withResultMap(twoAppsBothSuccessResultMap).build();

        Assert.assertNotNull(apps);
        Assert.assertEquals("Size not correct", 2, apps.size());

        SteamApp counterStrike = apps.get(0);
        SteamApp teamFortressClassic = apps.get(1);

        Assert.assertEquals("Name of first game", "Counter-Strike", counterStrike.getName());
        Assert.assertEquals("Name of second game", "Team Fortress Classic", teamFortressClassic.getName());
    }

    @Test
    public void shouldCreateOneSteamAppFromTwoRequestedButOnlyOneSuccessfullyRetrieved() {
        List<SteamApp> apps = builder.withResultMap(twoAppsOneSuccessResultMap).build();

        Assert.assertNotNull(apps);
        Assert.assertEquals("Size not correct", 1, apps.size());
    }
}
