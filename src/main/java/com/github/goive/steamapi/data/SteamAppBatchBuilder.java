package com.github.goive.steamapi.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SteamAppBatchBuilder {

    private List<Map<Object, Object>> dataMaps = new ArrayList<Map<Object, Object>>();

    @SuppressWarnings("unchecked")
    public SteamAppBatchBuilder withResultMap(Map<Object, Object> resultMap) {
        Set<Object> keySet = resultMap.keySet();
        for (Object key : keySet) {
            Long appId = Long.parseLong((String)key);
            Map<Object, Object> innerMap = (Map<Object, Object>)resultMap.get(appId + "");

            if ((Boolean)innerMap.get("success")) {
                HashMap<Object, Object> singleResultMap = new HashMap<Object, Object>();
                singleResultMap.put(key, resultMap.get(key));

                dataMaps.add(singleResultMap);
            }
        }

        return this;
    }

    public List<SteamApp> build() {
        SteamAppSingleBuilder singleBuilder = new SteamAppSingleBuilder();

        List<SteamApp> result = new ArrayList<SteamApp>();
        dataMaps.stream().forEach((dataMap) -> {
            result.add(singleBuilder.withResultMap(dataMap).build());
        });

        return result;
    }

}
