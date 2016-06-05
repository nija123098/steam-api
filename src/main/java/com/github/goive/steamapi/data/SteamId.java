package com.github.goive.steamapi.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by Ive on 05.06.2016.
 */
public class SteamId {

    private String appId;
    private String appName;

    /**
     * Shortcut method for creating an instance of {@link SteamId}.
     *
     * @param appId The appId as it appears in the URL on the steam page
     * @return Instance of {@link SteamId}
     */
    public static SteamId create(String appId) {
        return new SteamId(appId, "Unnamed Application");
    }

    /**
     * Legacy method for creating steamIds from long.
     *
     * @param appId The appId as it appears in the URL on the steam page
     * @return Instance of {@link SteamId}
     */
    public static SteamId create(long appId) {
        return new SteamId(Long.toString(appId), "Unnamed Application");
    }

    /**
     * Shortcut method for creating an instance of {@link SteamId}.
     *
     * @param appId   The appId as it appears in the URL on the steam page
     * @param appName An optional name for the application
     * @return Instance of {@link SteamId}
     */
    public static SteamId create(String appId, String appName) {
        return new SteamId(appId, appName);
    }

    private SteamId(String appId, String appName) {
        this.appId = appId;
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SteamId steamId = (SteamId) o;

        return new EqualsBuilder()
                .append(appId, steamId.appId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(appId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("appId", appId)
                .append("appName", appName)
                .toString();
    }

}
