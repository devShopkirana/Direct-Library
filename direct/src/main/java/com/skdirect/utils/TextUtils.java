package com.skdirect.utils;

import java.util.ArrayList;
import java.util.Map;

public class TextUtils {
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    private static final String MOBILE_NO_PATTERN = "^[+]?[0-9]{10,13}$";

    public static boolean isValidEmail(final String hex) {
        return hex.matches(EMAIL_PATTERN);
    }

    public static boolean isValidMobileNo(final String hex) {
        return hex.matches(MOBILE_NO_PATTERN);
    }

    public static int parseNullSafeInteger(String numberString) {
        int number = 0;
        try {
            Integer.parseInt(numberString);
        } catch (Exception e) {
        }
        return number;
    }

    public static boolean isIntegerValue(String numberString) {
        boolean result = false;
        try {
            Integer.parseInt(numberString);
            result = true;
        } catch (Exception e) {
        }
        return result;
    }

    public static boolean isInteger_GreaterThen0Value(String numberString) {
        boolean result = false;
        try {
            if (Integer.parseInt(numberString) > 0)
                result = true;
        } catch (Exception e) {
        }
        return result;
    }

    public static <T> boolean isNull(T t) {
        return t == null;
    }

    public static boolean isNullOrEmpty(String s) {
        return (s == null) || (s.length() == 0) || (s.equalsIgnoreCase("null")) || (s.equalsIgnoreCase("0"));
    }

    public static <T> boolean isNullOrEmpty(ArrayList<T> a) {
        return (a == null) || (a.size() == 0);
    }

    public static <T, Y> boolean isNullOrEmpty(Map<T, Y> m) {
        return (m == null) || (m.size() == 0);
    }
}