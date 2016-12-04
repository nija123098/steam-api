package com.github.goive.steamapi.utils;

import org.junit.Test;

import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

public class DateParserUtilTest {

    private DateParserUtil util;

    @Test
    public void shouldParseUSLocale() {
        util = new DateParserUtil(Locale.US);

        Date parseDate = util.parseDate("May 8, 2008");

        assertEquals(createDate(2008, 5, 8), parseDate);
    }

    @Test
    public void shouldParseUSLocaleWithTwoDigits() {
        util = new DateParserUtil(Locale.US);

        Date parseDate = util.parseDate("May 29, 2008");

        assertEquals(createDate(2008, 5, 29), parseDate);
    }

    @Test
    public void shouldParseEULocale() {
        util = new DateParserUtil(Locale.US);

        Date parseDate = util.parseDate("8 May, 2008");

        assertEquals(createDate(2008, 5, 8), parseDate);
    }

    @Test
    public void shouldParseEULocaleWithTwoDigits() {
        util = new DateParserUtil(Locale.US);

        Date parseDate = util.parseDate("16 May, 2008");

        assertEquals(createDate(2008, 5, 16), parseDate);
    }

    // the 1.6 date api sucks, hence this
    private Date createDate(int year, int month, int date) {
        return new Date(year - 1900, month - 1, date);
    }

}
