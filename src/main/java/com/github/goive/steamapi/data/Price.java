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

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

}
