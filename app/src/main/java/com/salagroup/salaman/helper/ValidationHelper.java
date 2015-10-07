package com.salagroup.salaman.helper;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.StringTokenizer;
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

    public static boolean checkViNameString(String s) {
        Pattern pattern = Pattern.compile("[^" + Constant.VIETNAMESE_DIACRITIC_CHARACTERS + Constant.ALPHABET_CHARACTERS + " " + "]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        return !matcher.find();
    }

    public static String getValidSpacesString(String originalString) {

        StringTokenizer tokenizer = new StringTokenizer(originalString, " ");
        String validString = "";
        while (tokenizer.hasMoreTokens()) {
            validString += tokenizer.nextToken() + " ";
        }
        return validString.trim();
    }

    public static String getValidPhoneNumber(String phoneNumber) {

        phoneNumber = phoneNumber.replace(" ","");
        phoneNumber = phoneNumber.replace("-", "");
        phoneNumber = phoneNumber.replace("+", "");
        phoneNumber = phoneNumber.replace("*", "");
        phoneNumber = phoneNumber.replace("/", "");
        phoneNumber = phoneNumber.replace("#", "");
        phoneNumber = phoneNumber.replace("(", "");
        phoneNumber = phoneNumber.replace(")", "");
        phoneNumber = phoneNumber.replace(",", "");
        phoneNumber = phoneNumber.replace(".", "");
        phoneNumber = phoneNumber.replace(";", "");

        return phoneNumber;
    }

    public static void addValidSpacesTextChanged(final EditText edt) {

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String name = edt.getText().toString();
                if (name.length() != 0) {

                    String newChar = name.substring(name.length() - 1);
                    if (newChar.equals(" ")) {

                        try {

                            String lastChar = name.substring(name.length() - 2, name.length() - 1);
                            if (lastChar.equals(" ")) {

                                edt.setText(name.substring(0, name.length() - 1));
                                edt.setSelection(name.length() - 1);
                            }
                        } catch (Exception ex) {

                            edt.setText(name.substring(0, name.length() - 1));
                            edt.setSelection(name.length() - 1);
                        }
                    }
                }
            }
        });
    }
}
