package com.github.goive.steamapi;

import com.github.goive.steamapi.client.SteamApiClient;
import com.github.goive.steamapi.data.SteamApp;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class SteamApiTest {

    private Map<Object, Object> halfLifeResultMap;

    @Mock
    private SteamApiClient steamApiClient;

    @InjectMocks
    private SteamApiImpl steamApiImpl;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        halfLifeResultMap = objectMapper.readValue(new File("src/test/resources/app_id_70.json"), Map.class);
    }

    @Test
    public void shouldRetrieveDataForOneId() {
        Mockito.when(steamApiClient.retrieveResultBodyMap(70L)).thenReturn(halfLifeResultMap);

        SteamApp halfLife = steamApiImpl.retrieveApp(70L);

        Assert.assertEquals("Name not correct", "Half-Life", halfLife.getName());
    }

}
