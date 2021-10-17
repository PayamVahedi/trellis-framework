package com.hamrasta.trellis.validator;

import com.hamrasta.trellis.util.phone.PhoneUtil;
import com.hamrasta.trellis.core.metadata.Country;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.multipart.MultipartFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.TimeZone;

public class Validator {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,10}$";
    private static final String SHAMSI_DATE_PATTERN = "^$|^([1][0-9]{3}[/\\/]([0][1-6])[/\\/]([0][1-9]|[12][0-9]|[3][01])|[1][0-9]{3}[/\\/]([0][7-9]|[1][012])[/\\/]([0][1-9]|[12][0-9]|(30)))$";
    private static final String TIME_PATTERN = "^(0[0-9]|1[0-9]|2[0-3]|[0-9])(:[0-5][0-9]|:[0-5][0-9]:[0-5][0-9])$";
    private static final String DATE_TIME_PATTERN = "^[0-9]{4}[/\\/](0[1-9]|1[0-2])[/\\/](0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9])(:[0-5][0-9]|:[0-5][0-9]:[0-5][0-9])$";
    public static final List<String> IMAGE_TYPE_LIST = Arrays.asList("image/jpeg", "image/png");
    private static final int IBAN_MIN_SIZE = 15;
    private static final int IBAN_MAX_SIZE = 34;
    private static final long IBAN_MAX = 999999999;
    private static final long IBAN_MODULUS = 97;

    public static boolean isEmail(String email) {
        return isValid(email, EMAIL_PATTERN);
    }

    public static boolean isPhone(String mobile) {
        return PhoneUtil.isValid(mobile);
    }

    public static boolean isPhone(String mobile, Country... countries) {
        return PhoneUtil.isValid(mobile, countries);
    }

    public static boolean isValid(String s, String pattern) {
        if (StringUtils.isEmpty(s) || StringUtils.isEmpty(pattern))
            return false;
        Pattern ptrn = Pattern.compile(pattern);
        return ptrn.matcher(s).matches();
    }

    public static boolean isValidNationalCode(String s) {
        if (StringUtils.isEmpty(s) || !NumberUtils.isDigits(s))
            return false;
        if (s.length() != 10)
            return false;
        int sum = 0;
        int controlDigit = -1;
        String characters = StringUtils.reverse(s);
        char[] array = characters.toCharArray();
        for (int i = 0; i < array.length; i++) {
            int digit = Integer.parseInt(String.valueOf(array[i]));
            if (i == 0) {
                controlDigit = digit;
                continue;
            }
            sum += (digit * (i + 1));
        }
        int rem = sum % 11;
        if (rem < 2)
            return rem == controlDigit;
        else
            return (11 - rem) == controlDigit;
    }

    public static boolean isValidZipCode(String s) {
        return StringUtils.isEmpty(s) || !NumberUtils.isDigits(s) || s.length() != 10 ? false : true;
    }

    public static boolean isImage(MultipartFile file) {
        return IMAGE_TYPE_LIST.contains(file.getContentType());
    }

    public static boolean isValidIBan(String s) {
        if (StringUtils.isEmpty(s))
            return false;
        String trimmed = s.trim();
        String country = trimmed.length() < 2 ? StringUtils.EMPTY : trimmed.substring(0, 2);
        boolean hasCountry = StringUtils.isNotBlank(country) && StringUtils.isAlpha(country);
        trimmed = hasCountry ? trimmed : "IR" + trimmed;
        if (trimmed.length() < IBAN_MIN_SIZE || trimmed.length() > IBAN_MAX_SIZE) {
            return false;
        }
        String reformat = trimmed.substring(4) + trimmed.substring(0, 4);
        long total = 0;
        for (int i = 0; i < reformat.length(); i++) {
            int charValue = Character.getNumericValue(reformat.charAt(i));
            if (charValue < 0 || charValue > 35) {
                return false;
            }
            total = (charValue > 9 ? total * 100 : total * 10) + charValue;
            if (total > IBAN_MAX) {
                total = (total % IBAN_MODULUS);
            }
        }
        return (total % IBAN_MODULUS) == 1;
    }

    public static boolean isValidBankCardNumber(String s) {
        if (StringUtils.isBlank(s) || s.length() != 16 || !NumberUtils.isDigits(s)) {
            return false;
        }
        var cardTotal = 0;
        for (var i = 0; i < 16; i += 1) {
            int c = Integer.parseInt(String.valueOf(s.charAt(i)));
            if (i % 2 == 0) {
                cardTotal += ((c * 2 > 9) ? (c * 2) - 9 : (c * 2));
            } else {
                cardTotal += c;
            }
        }
        return (cardTotal % 10 == 0);
    }

    public static boolean isValidDate(String value) {
        return isValidShamsiDate(value) || isValidMiladiDate(value);
    }

    public static boolean isValidShamsiDate(String value) {
        return isValid(value, SHAMSI_DATE_PATTERN);
    }

    public static boolean isValidMiladiDate(String value) {
        return isValidMiladiDate(value, "yyyy/MM/dd");
    }

    public static boolean isValidMiladiDate(String value, String dateFormat) {
        if (value == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setLenient(false);
        try {
            Date date = sdf.parse(value);
            System.out.println(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidTime(String value) {
        return isValid(value, TIME_PATTERN);
    }

    public static boolean isValidDateTime(String value) {
        return isValid(value, DATE_TIME_PATTERN);
    }

    public static boolean isValidTimeZone(String value) {
        return StringUtils.isNotBlank(value) && Set.of(TimeZone.getAvailableIDs()).contains(value);
    }
}
