package com.github.goive.steamapi.client;

import com.github.goive.steamapi.exceptions.SteamApiException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Created by ive on 14.02.15.
 */
public class AppIdChecker {

    private static final Logger logger = Logger.getLogger(AppIdChecker.class);

    private final ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings("unchecked")
    private Map<Object, Object> fetchResultMap() {
        Map<Object, Object> resultMap;

        try {
            URL src = new URL("http://api.steampowered.com/ISteamApps/GetAppList/v0001/");
            resultMap = mapper.readValue(src, Map.class);
        } catch (IOException e) {
            throw new SteamApiException(e);
        }

        return resultMap;
    }

    public boolean existsAppIdOnSteam(long appId) {
        List appList = parseAppIdList();
        logger.debug(appList.size() + " appIds returned from API");
        for (Object app : appList) {
            Map appItem = (Map) app;
            long retrievedAppId = Long.parseLong(appItem.get("appid").toString());

            if (retrievedAppId == appId) {
                return true;
            }
        }

        return false;
    }

    private List parseAppIdList() {
        Map<Object, Object> resultMap = fetchResultMap();
        Map appMap = (Map) resultMap.get("applist");
        Map apps = (Map) appMap.get("apps");
        return (List) apps.get("app");
    }

}
