package com.github.goive.steamapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goive.steamapi.data.SteamApp;
import com.github.goive.steamapi.data.SteamAppBuilder;
import com.github.goive.steamapi.exceptions.SteamApiException;
import com.github.goive.steamapi.utils.AppIdMatcherUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.helpers.NOPLoggerFactory;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SteamApi {

    private static final String APP_ID_LIST_URL = "http://api.steampowered.com/ISteamApps/GetAppList/v0001/";
    private static final String API_URL = "http://store.steampowered.com/api/appdetails?appids=";

    private ObjectMapper mapper = new ObjectMapper();
    private String countryCode;
    private Map<Integer, String> appCache = new HashMap<>();
    private AppIdMatcherUtil matcherUtil = new AppIdMatcherUtil(appCache);

    public SteamApi() {
        this(Locale.getDefault().getCountry());
    }

    public SteamApi(String countryCode) {
        setCountryCode(countryCode);
    }

    /**
     * Retrieves a steam game using its appId.
     *
     * @param appId The id of the game
     * @return {@link SteamApp} containing information of the game
     * @throws SteamApiException if invalid appId or service not available
     */
    public SteamApp retrieve(int appId) throws SteamApiException {
        Map resultBodyMap;

        try {
            URL src = new URL(API_URL + appId + "&cc=" + countryCode);
            resultBodyMap = mapper.readValue(src, Map.class);
        } catch (IOException e) {
            throw new SteamApiException(e);
        }

        if (!successfullyRetrieved(resultBodyMap, appId)) {
            throw new SteamApiException("Invalid appId given: " + appId);
        }

        return new SteamAppBuilder(resultBodyMap)
                .withCountryCode(countryCode)
                .build();
    }

    private boolean successfullyRetrieved(Map resultBodyMap, int appId) {
        return Boolean.valueOf(((Map) resultBodyMap.get(String.valueOf(appId))).get("success").toString());
    }

    /**
     * Retrieves a steam game using its app name.
     * <p>
     * <b>Use this method with caution!</b> If no matching entry is found, this method will take any appName
     * that has the string's best levensthein distance, even if it has no relation to the search term. Consider
     * this method as experimental.
     * <p>
     * There are games on steam with exactly the same name and different
     * appIds. If you want to be sure which game you mean, use the appId instead of the appName to retrieve it.
     * The appId can be found in the URL of the game's entry on the steam page.
     *
     * @param appName The full or partial name of the game
     * @return {@link SteamApp} containing information of the game
     * @throws SteamApiException if no appId could be found or service not available
     */
    public SteamApp retrieve(String appName) throws SteamApiException {
        if (StringUtils.isBlank(appName)) {
            throw new IllegalArgumentException("appName cannot be empty");
        }

        if (appCache.isEmpty()) {
            buildAppCache();
        }

        String bestMatch = matcherUtil.findBestMatch(appName);
        for (Integer appId : appCache.keySet()) {
            if (bestMatch.equals(appCache.get(appId))) {
                return retrieve(appId);
            }
        }

        throw new SteamApiException("No appId found for given app name: " + appName);
    }

    /**
     * Retrieves a map of all possible apps.
     *
     * @return {@link Map} of existing appIds and game names
     * @throws SteamApiException if service not available
     */
    public Map<Integer, String> listApps() throws SteamApiException {
        return listApps(false);
    }

    /**
     * Retrieves a map of all possible apps.
     *
     * @param forceRefresh ensure that the newest ids are fetched
     * @return {@link Map} of existing appIds and game names
     * @throws SteamApiException if service not available
     */
    public Map<Integer, String> listApps(boolean forceRefresh) throws SteamApiException {
        if (forceRefresh) {
            buildAppCache();
        }

        if (!appCache.isEmpty()) {
            return new HashMap<>(appCache);
        }

        buildAppCache();

        return new HashMap<>(appCache);
    }

    private void buildAppCache() throws SteamApiException {
        Map<Object, Object> resultMap;
        try {
            URL src = new URL(APP_ID_LIST_URL);
            resultMap = mapper.readValue(src, Map.class);
        } catch (IOException e) {
            throw new SteamApiException("Error fetching list of valid AppIDs.", e);
        }

        Map appMap = (Map) resultMap.get("applist");
        Map apps = (Map) appMap.get("apps");
        List appList = (List) apps.get("app");

        for (Object app : appList) {
            Map appItem = (Map) app;
            appCache.put((Integer) appItem.get("appid"), appItem.get("name").toString());
        }
    }

    public void setCountryCode(String countryCode) {
        if (StringUtils.isBlank(countryCode)) {
            throw new IllegalArgumentException("Country code cannot be empty.");
        }

        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public static Logger getLoggerInstance(Class<?> clazz){
        try {
            Class.forName("org.slf4j.impl.StaticLoggerBinder");
            if (!(LoggerFactory.getILoggerFactory() instanceof NOPLoggerFactory)) return LoggerFactory.getLogger(clazz);
        } catch (ClassNotFoundException ignored) {}
        return new SteamApiLogger(clazz.getSimpleName() + "Logger"); //No implementation found
    }

    public static class SteamApiLogger extends MarkerIgnoringBase {
        private final String name;
        private SteamApiLogger(String name) {
            this.name = name;
        }

        private void log(Level level, String message, Throwable error) {
            PrintStream stream = level.ordinal() >= Level.WARN.ordinal() ? System.err : System.out;
            stream.format("%s: [%s][%s][%s] - %s\n", LocalTime.now(), level, Thread.currentThread().getName(), name, message);
            if (error != null) error.printStackTrace(stream);
        }

        @Override
        public boolean isTraceEnabled() {
            return false;
        }

        @Override
        public void trace(String msg) {
        }

        @Override
        public void trace(String format, Object arg) {
        }

        @Override
        public void trace(String format, Object arg1, Object arg2) {
        }

        @Override
        public void trace(String format, Object... arguments) {
        }

        @Override
        public void trace(String msg, Throwable t) {
        }

        @Override
        public boolean isDebugEnabled() {
            return false;
        }

        @Override
        public void debug(String msg) {
        }

        @Override
        public void debug(String format, Object arg) {
        }

        @Override
        public void debug(String format, Object arg1, Object arg2) {
        }

        @Override
        public void debug(String format, Object... arguments) {
        }

        @Override
        public void debug(String msg, Throwable t) {
            log(Level.DEBUG, msg, t);
        }

        @Override
        public boolean isInfoEnabled() {
            return true;
        }

        @Override
        public void info(String msg) {
            log(Level.INFO, msg, null);
        }

        @Override
        public void info(String format, Object arg) {
        }

        @Override
        public void info(String format, Object arg1, Object arg2) {
        }

        @Override
        public void info(String format, Object... arguments) {
        }

        @Override
        public void info(String msg, Throwable t) {
        }

        @Override
        public boolean isWarnEnabled() {
            return true;
        }

        @Override
        public void warn(String msg) {
            log(Level.WARN, msg, null);
        }

        @Override
        public void warn(String format, Object arg) {
        }

        @Override
        public void warn(String format, Object arg1, Object arg2) {
        }

        @Override
        public void warn(String format, Object... arguments) {
        }

        @Override
        public void warn(String msg, Throwable t) {
            log(Level.WARN, msg, t);
        }

        @Override
        public boolean isErrorEnabled() {
            return true;
        }

        @Override
        public void error(String msg) {
        }

        @Override
        public void error(String format, Object arg) {
        }

        @Override
        public void error(String format, Object arg1, Object arg2) {
        }

        @Override
        public void error(String format, Object... arguments) {
        }

        @Override
        public void error(String msg, Throwable t) {
        }

        private enum Level {
            DEBUG, INFO, WARN
        }
    }

}
