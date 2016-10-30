package com.github.goive.steamapi.utils;

import com.github.goive.steamapi.exceptions.SteamApiException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppIdMatcherUtil {

    private Map<Integer, String> appCache;

    public AppIdMatcherUtil(Map<Integer, String> appCache) {
        this.appCache = appCache;
    }

    public String findBestMatch(String appName) throws SteamApiException {
        List<String> entriesContainingAppName = findBestMatches(appName);

        if (entriesContainingAppName.isEmpty()) {
            return getBestMatchBasedOnLevenshteinDistance(appName, appCache.values());
        } else {
            return getBestMatchBasedOnLevenshteinDistance(appName, entriesContainingAppName);
        }
    }

    public List<String> findBestMatches(String appName) {
        return appCache.values()
                .stream()
                .filter(existingAppName -> StringUtils.containsIgnoreCase(existingAppName, appName))
                .collect(Collectors.toList());
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
