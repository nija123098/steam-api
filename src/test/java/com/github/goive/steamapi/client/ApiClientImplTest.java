package com.github.goive.steamapi.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goive.steamapi.exceptions.SteamApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URL;

/**
 * Created by ive on 17.02.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ApiClientImplTest {

    @InjectMocks
    private ApiClientImpl client;

    private class ObjectMapperStub extends ObjectMapper {
        public <T> T readValue(URL src, Class<T> valueType) throws IOException {
            throw new IOException();
        }
    }

    @Test(expected = SteamApiException.class)
    public void shouldThrowExceptionIfValidAppIdsCannotBeRetrieved() throws IOException {
        ObjectMapperStub mapperStub = new ObjectMapperStub();
        client.setMapper(mapperStub);

        client.retrieveValidAppIds();
    }

}
