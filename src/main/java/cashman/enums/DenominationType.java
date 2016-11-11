package cashman.enums;

import cashman.util.Constant;

import java.math.BigDecimal;

public enum DenominationType {

    FIVE_CENT(0.05),
    TEN_CENT(0.1),
    TWENTY_CENT(0.2),
    FIFTY_CENT(0.5),
    ONE_DOLLAR(1),
    TWO_DOLLAR(2),
    FIVE_DOLLAR(5),
    TEN_DOLLAR(10),
    TWENTY_DOLLAR(20),
    FIFETY_DOLLAR(50),
    ONE_HUNDRED_DOLLAR(100);

    private double value;

    DenominationType(double value) {
        this.value = value;
    }

    public BigDecimal value() {
        return BigDecimal.valueOf(value);
    }

    public Double valuedouble() {
        return Double.valueOf(value);
    }

    public static DenominationType findByValue(Double amount) {

        for (DenominationType value : values() ) {
            if (value.valuedouble().compareTo(amount) == Constant.ZERO ) {
                return value;
            }
        }
        return null;
    }
}

