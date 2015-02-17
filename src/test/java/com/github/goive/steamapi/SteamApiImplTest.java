package com.github.goive.steamapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goive.steamapi.client.ApiClient;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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

        Set<Long> appIds = new HashSet<>();
        appIds.add(123L);
        appIds.add(70L);
        when(apiClient.retrieveValidAppIds()).thenReturn(appIds);
    }

    @Test
    public void shouldRetrieveDataForOneId() {
        when(apiClient.retrieveResultBodyMap(70L)).thenReturn(halfLifeResultMap);

        SteamApp halfLife = steamApiImpl.retrieveApp(70L);

        assertEquals("Name not correct", "Half-Life", halfLife.getName());
    }

    @Test
    public void shouldRetrieveListOfValidAppIdsOnFirstCall() {
        steamApiImpl.retrieveApp(123L);

        verify(apiClient, times(1)).retrieveValidAppIds();
    }

    @Test
    public void shouldNotRetrieveListOfValidAppIdsIfIdsInList() {
        Set<Long> appIds = new HashSet<>();
        appIds.add(123L);

        steamApiImpl.setValidAppIds(appIds);

        steamApiImpl.retrieveApp(123L);

        verify(apiClient, never()).retrieveValidAppIds();
    }

    @Test(expected = SteamApiException.class)
    public void shouldThrowExceptionOnInvalidAppId() {
        steamApiImpl.retrieveApp(999999999L);
    }
}
