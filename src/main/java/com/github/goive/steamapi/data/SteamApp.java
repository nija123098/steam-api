package com.github.goive.steamapi.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Represents an application entry from the Steam database. Fields may be empty if there was no entry in the
 * corresponding JSON object.
 *
 * @author Ivan Antes-Klobucar
 * @version 2.1
 */
public final class SteamApp implements Comparable<SteamApp> {

    private final long appId;
    private final String type;
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
    private final List<String> categories;
    private final Date releaseDate;
    private final Integer metacriticScore;
    private final String metacriticUrl;
    private final String supportUrl;
    private final String supportEmail;
    private final List<String> genres;

    SteamApp(long appId, String type, String name, int requiredAge, String detailedDescription, String aboutTheGame,
             List<String> supportedLanguages, String headerImage, String website, Price price, List<String> developers,
             List<String> publishers, boolean availableForLinux, boolean availableForWindows, boolean availableForMac,
             List<String> categories, Date releaseDate, Integer metacriticScore, String metacriticUrl,
             String supportUrl, String supportEmail, List<String> genres) {
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
        this.supportUrl = supportUrl;
        this.supportEmail = supportEmail;
        this.genres = genres;
    }

    public long getAppId() {
        return appId;
    }

    public String getType() {
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
     * @return A list of {@link String} objects containing categories like "single player".
     */
    public List<String> getCategories() {
        return Collections.unmodifiableList(categories);
    }

    public String getSupportUrl() {
        return supportUrl;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public List<String> getGenres() {
        return Collections.unmodifiableList(genres);
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

        return price.getFinalPrice().compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Convenient check if the game is discounted.
     *
     * @return true if discount percent is greater than 0; false if free to play
     */
    public boolean isDiscounted() {
        return isDiscountedByAtLeast(1);
    }

    /**
     * Convenient check if the game is discounted by at least the given percentage.
     *
     * @param percent Percentage of the discount
     * @return true if discounted by given percentage or higher
     */
    public boolean isDiscountedByAtLeast(int percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }

        if (price == null) {
            return false;
        }

        return price.getDiscountPercent() >= percent;
    }

    /**
     * Convenient check if the app is in any of the given categories.
     *
     * @param categories Categories to be checked
     * @return true if app has any of the given categories
     */
    public boolean isInAnyCategory(String... categories) {
        if (this.categories == null) {
            return false;
        }

        for (String category : categories) {
            if (this.categories.contains(category)) {
                return true;
            }
        }

        return false;
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

        SteamApp other = (SteamApp) obj;

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
                .append(supportUrl) //
                .append(supportEmail) //
                .append(genres) //
                .toString();
    }

    @Override
    public int compareTo(SteamApp other) {
        return (int) (this.appId - other.appId);
    }

}
