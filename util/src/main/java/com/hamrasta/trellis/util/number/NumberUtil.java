package com.hamrasta.trellis.util.number;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Random;

public class NumberUtil {

    public static String getRandom(int length) {
        if (length < 1 || length > 12) {
            length = 6;
        }
        Random random = new Random();
        long nextLong = Math.abs(random.nextLong());
        return String.valueOf(nextLong).substring(0, length);
    }

    public static String toPlainString(BigDecimal value, int scale) {
        return toPlainString(value, scale, false);
    }

    public static String toPlainString(BigDecimal value, int scale, boolean zeroIfNull) {
        if (zeroIfNull)
            value = zeroIfNull(value);
        return value == null ? StringUtils.EMPTY : value.setScale(scale, RoundingMode.HALF_EVEN).toPlainString();
    }

    public static BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static BigDecimal toBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = BigDecimal.valueOf(((Number) value).doubleValue());
            }
        }
        return ret;
    }

}
