package com.hamrasta.trellis.util.text;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class TextUtil {
    public static String toUTF8(String msg) {
        msg = persianFix(toEnglishNumber(msg));
        if (StandardCharsets.ISO_8859_1.newEncoder().canEncode(msg)) {
            return new String(msg.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
        }
        return msg;
    }

    public static String persianFix(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        } else {
            return text.replaceAll("\u06AA", "ک")
                    .replaceAll("\u0643", "ک")
                    .replaceAll("\u0649", "ی")
                    .replaceAll("\u064A", "ی");
        }
    }

    public static String toEnglishNumber(String text) {
        if (StringUtils.isEmpty(text)) {
            return "";
        } else {
            return text.replaceAll("۰", "0")
                    .replaceAll("۱", "1")
                    .replaceAll("۲", "2")
                    .replaceAll("۳", "3")
                    .replaceAll("۴", "4")
                    .replaceAll("۵", "5")
                    .replaceAll("۶", "6")
                    .replaceAll("۷", "7")
                    .replaceAll("۸", "8")
                    .replaceAll("۹", "9");
        }
    }

    public static byte[] hexStr2Bytes(String hex) {
        byte[] bArray = new BigInteger("10" + hex, 16).toByteArray();
        byte[] ret = new byte[bArray.length - 1];
        System.arraycopy(bArray, 1, ret, 0, ret.length);
        return ret;
    }

    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getRandom(int characterLength) {
        return UUID.randomUUID().toString().substring(0, characterLength);
    }


}
