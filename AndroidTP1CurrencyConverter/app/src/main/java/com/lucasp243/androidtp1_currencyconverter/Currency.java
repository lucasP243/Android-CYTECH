package com.lucasp243.androidtp1_currencyconverter;

public enum Currency {

    USD("USD", "US$", 1.00),
    EUR("EUR", "€", 1.03),
    JPY("JPY", "¥", 0.0071),
    GBP("GBP", "£", 1.18),
    CNY("CNY", "¥", 0.14),
    AUD("AUD", "A$", 0.67),
    CAD("CAD", "C$", 0.75),
    CHF("CHF", "CHF", 1.06),
    HKD("HKD", "HK$", 0.13),
    SGD("SGD", "S$", 0.73);

    private String codeISO4217;

    private String symbol;

    private Double rateToUSD;

    Currency(String codeISO4217, String symbol, double rateToUSD) {
        this.codeISO4217 = codeISO4217;
        this.symbol = symbol;
        this.rateToUSD = rateToUSD;
    }

    public String getCodeISO4217() {
        return codeISO4217;
    }

    public String getSymbol() {
        return symbol;
    }

    public Double getRateToUSD() {
        return rateToUSD;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", codeISO4217, symbol);
    }
}
