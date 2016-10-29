package com.github.goive.steamapi.utils;

import com.github.goive.steamapi.exceptions.SteamApiException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AppIdMatcherUtil {

    private Map<Integer, String> appCache;

    public AppIdMatcherUtil(Map<Integer, String> appCache) {
        this.appCache = appCache;
    }

    public String findBestMatch(String appName) throws SteamApiException {
        Set<String> entriesContainingAppName = appCache.values()
                .stream()
                .filter(existingAppName -> StringUtils.containsIgnoreCase(existingAppName, appName))
                .collect(Collectors.toSet());

        if (entriesContainingAppName.isEmpty()) {
            return getBestMatchBasedOnLevenshteinDistance(appName, appCache.values());
        } else {
            return getBestMatchBasedOnLevenshteinDistance(appName, entriesContainingAppName);
        }
    }

    private String getBestMatchBasedOnLevenshteinDistance(String appName, Collection<String> entries) {
        String bestMatch = null;
        int bestDistance = 1000;
        for (String suggestion : entries) {
            int distance = StringUtils.getLevenshteinDistance(suggestion, appName);
            if (distance < bestDistance) {
                bestMatch = suggestion;
                bestDistance = distance;
            }
        }

        return bestMatch;
    }

}
