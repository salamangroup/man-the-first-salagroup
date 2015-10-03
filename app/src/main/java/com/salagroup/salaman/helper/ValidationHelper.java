package com.salagroup.salaman.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationHelper {

    public static boolean isEnString(String s) {
        Pattern pattern = Pattern.compile("[^" + Constant.DIGITS + Constant.ALPHABET_CHARACTERS + Constant.ALLOW_SYMBOLS + "]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }

    public static boolean isViString(String s) {
        Pattern pattern = Pattern.compile("[^" + Constant.VIETNAMESE_DIACRITIC_CHARACTERS + Constant.DIGITS + Constant.ALPHABET_CHARACTERS + Constant.ALLOW_SYMBOLS + "]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }
}
