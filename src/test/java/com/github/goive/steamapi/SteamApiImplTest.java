package com.github.goive.steamapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goive.steamapi.client.ApiClient;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SteamApiImplTest {

    private Map<Object, Object> halfLifeResultMap;

    @Mock
    private ApiClient apiClient;

    @InjectMocks
    private SteamApiImpl steamApiImpl;

    @SuppressWarnings("unchecked")
    @Before
    public void setup() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        halfLifeResultMap = objectMapper.readValue(new File("src/test/resources/app_id_70.json"), Map.class);

        List<SteamId> appIds = new ArrayList<>();
        appIds.add(SteamId.create(123L));
        appIds.add(SteamId.create(70L));
        when(apiClient.retrieveAllAppIds()).thenReturn(appIds);
    }

    @Test
    public void shouldRetrieveDataForOneId() {
        when(apiClient.retrieveResultBodyMap(SteamId.create(70L))).thenReturn(halfLifeResultMap);

        SteamApp halfLife = steamApiImpl.retrieveApp(70L);

        assertEquals("Name not correct", "Half-Life", halfLife.getName());
    }

}
