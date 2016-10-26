package com.github.goive.steamapi.data;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * Represents pricing data for the application and contains relevant fields.
 *
 * @author Ivan Antes-Klobucar
 * @version 2.1
 */
public class Price {

    private Currency currency;
    private BigDecimal initialPrice;
    private BigDecimal finalPrice;
    private int discountPercent;

    Price(Currency currency, BigDecimal initialPrice, BigDecimal finalPrice, int discountPercent) {
        this.currency = currency;
        this.initialPrice = initialPrice;
        this.finalPrice = finalPrice;
        this.discountPercent = discountPercent;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

}
