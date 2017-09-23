package com.github.goive.steamapi.utils;

import com.github.goive.steamapi.SteamApi;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateParserUtil {

    private final Logger logger = SteamApi.getLoggerInstance(DateParserUtil.class);

    private static final String PATTERN_EU = "d MMM, yyyy";
    private static final String PATTERN_US = "MMM d, yyyy";

    private static final String REGEX_PATTERN_EU = "\\d{1,2}\\s\\w{3}\\,\\s\\d{4}";
    private static final String REGEX_PATTERN_US = "\\w{3}\\s\\d{1,2}\\,\\s\\d{4}";

    private Pattern euPattern;
    private Pattern usPattern;

    private Locale locale;

    public DateParserUtil(Locale locale) {
        this.locale = locale;
        euPattern = Pattern.compile(REGEX_PATTERN_EU);
        usPattern = Pattern.compile(REGEX_PATTERN_US);
    }

    public Date parseDate(String dateString) {
        if (StringUtils.isBlank(dateString)) {
            logger.warn("Date string is empty.");
            return null;
        }

        if (!euPattern.matcher(dateString).matches() && !usPattern.matcher(dateString).matches()) {
            logger.warn("Custom date " + dateString + " cannot be parsed.");
            return null;
        }

        return matchInternal(dateString);
    }

    private Date matchInternal(String dateString) {
        if (usPattern.matcher(dateString).matches()) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_US, locale);
                return simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                logger.warn("Could not parse release date " + dateString, e);
            }
        }

        if (euPattern.matcher(dateString).matches()) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_EU, locale);
                return simpleDateFormat.parse(dateString);
            } catch (ParseException e) {
                logger.warn("Could not parse release date " + dateString, e);
            }
        }

        logger.warn("Date " + dateString + " was unparseable.");
        return null;
    }


}
