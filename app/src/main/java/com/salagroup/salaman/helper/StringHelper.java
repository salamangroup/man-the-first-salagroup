package com.salagroup.salaman.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringHelper {
    public static final String VIETNAMESE_DIACRITIC_CHARACTERS = "ẮẰẲẴẶĂẤẦẨẪẬÂÁÀÃẢẠĐẾỀỂỄỆÊÉÈẺẼẸÍÌỈĨỊỐỒỔỖỘÔỚỜỞỠỢƠÓÒÕỎỌỨỪỬỮỰƯÚÙỦŨỤÝỲỶỸỴ";
    public static final String ALPHABET_CHARACTERS = "A-Z";
    public static final String DIGITS = "0-9";
    public static final String ALLOW_SYMBOLS = " _-";

    public static boolean isEnString(String s) {
        Pattern pattern = Pattern.compile("[^" + DIGITS + ALPHABET_CHARACTERS + ALLOW_SYMBOLS + "]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }

    public static boolean isViString(String s) {
        Pattern pattern = Pattern.compile("[^" + VIETNAMESE_DIACRITIC_CHARACTERS + DIGITS + ALPHABET_CHARACTERS + ALLOW_SYMBOLS + "]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }
}
