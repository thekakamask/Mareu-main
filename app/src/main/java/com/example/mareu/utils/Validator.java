package com.example.mareu.utils;

import android.util.Patterns;

public class Validator {

    public static boolean validEmail (String  value) {
        return Patterns.EMAIL_ADDRESS.matcher(value.trim()).matches();

    }
}
