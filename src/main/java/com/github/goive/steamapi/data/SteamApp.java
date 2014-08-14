package com.github.goive.steamapi.data;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.github.goive.steamapi.enums.Type;

/**
 * Represents an application entry from the Steam database. Fields may be empty if there was no entry in the
 * corresponding JSON object.
 * 
 * @author Ivan Antes-Klobucar
 * @version 2.1
 */
public final class SteamApp implements Comparable<SteamApp> {

    private final long appId;
    private final Type type;
    private final String name;
    private final int requiredAge;
    private final String detailedDescription;
    private final String aboutTheGame;
    private final List<String> supportedLanguages;
    private final String headerImage;
    private final String website;
    private final Price price;
    private final List<String> developers;
    private final List<String> publishers;
    private final boolean availableForLinux;
    private final boolean availableForWindows;
    private final boolean availableForMac;
    private final List<Category> categories;
    private final Date releaseDate;
    private final Integer metacriticScore;
    private final String metacriticUrl;
    private final SupportInfo supportInfo;

    SteamApp(long appId, Type type, String name, int requiredAge, String detailedDescription, String aboutTheGame,
            List<String> supportedLanguages, String headerImage, String website, Price price, List<String> developers,
            List<String> publishers, boolean availableForLinux, boolean availableForWindows, boolean availableForMac,
            List<Category> categories, Date releaseDate, Integer metacriticScore, String metacriticUrl,
            SupportInfo supportInfo) {
        this.appId = appId;
        this.type = type;
        this.name = name;
        this.requiredAge = requiredAge;
        this.detailedDescription = detailedDescription;
        this.aboutTheGame = aboutTheGame;
        this.supportedLanguages = supportedLanguages;
        this.headerImage = headerImage;
        this.website = website;
        this.price = price;
        this.developers = developers;
        this.publishers = publishers;
        this.availableForLinux = availableForLinux;
        this.availableForWindows = availableForWindows;
        this.availableForMac = availableForMac;
        this.categories = categories;
        this.releaseDate = releaseDate;
        this.metacriticScore = metacriticScore;
        this.metacriticUrl = metacriticUrl;
        this.supportInfo = supportInfo;
    }

    public long getAppId() {
        return appId;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getRequiredAge() {
        return requiredAge;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public String getAboutTheGame() {
        return aboutTheGame;
    }

    public List<String> getSupportedLanguages() {
        return Collections.unmodifiableList(supportedLanguages);
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public String getWebsite() {
        return website;
    }

    public List<String> getDevelopers() {
        return Collections.unmodifiableList(developers);
    }

    public List<String> getPublishers() {
        return Collections.unmodifiableList(publishers);
    }

    public boolean isAvailableForLinux() {
        return availableForLinux;
    }

    public boolean isAvailableForWindows() {
        return availableForWindows;
    }

    public boolean isAvailableForMac() {
        return availableForMac;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public Integer getMetacriticScore() {
        return metacriticScore;
    }

    public String getMetacriticUrl() {
        return metacriticUrl;
    }

    /**
     * Returns pricing information of the application.
     * 
     * @return {@link Price} containing further pricing information. If empty, the application is free.
     */
    public Price getPrice() {
        return price;
    }

    /**
     * Returns a list of categories for the application.
     * 
     * @return A list of {@link Category} objects containing categories like "single player".
     */
    public List<Category> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    /**
     * Returns support information for the applications like e-mail address and url.
     * 
     * @return {@link SupportInfo} object containing data about the application.
     */
    public SupportInfo getSupportInfo() {
        return supportInfo;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder() //
            .append(appId) //
            .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof SteamApp)) {
            return false;
        }

        SteamApp other = (SteamApp)obj;

        return new EqualsBuilder() //
            .append(appId, other.appId) //
            .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(appId) //
            .append(type) //
            .append(name) //
            .append(requiredAge) //
            .append(detailedDescription) //
            .append(aboutTheGame) //
            .append(supportedLanguages) //
            .append(headerImage) //
            .append(website) //
            .append(price) //
            .append(developers) //
            .append(publishers) //
            .append(availableForLinux) //
            .append(availableForWindows) //
            .append(availableForMac) //
            .append(categories) //
            .append(releaseDate) //
            .append(metacriticScore) //
            .append(metacriticUrl) //
            .append(supportInfo) //
            .toString();
    }

    @Override
    public int compareTo(SteamApp other) {
        return (int)(this.appId - other.appId);
    }

    /**
     * Convenient check for free to play game.
     * 
     * @return true if game is free to play.
     */
    public boolean isFreeToPlay() {
        if (price == null) {
            return true;
        }

        return false;
    }

}
